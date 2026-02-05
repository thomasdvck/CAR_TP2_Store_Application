package TP1.storeApplication.ctrl;

import TP1.storeApplication.entity.Client;
import TP1.storeApplication.entity.Commande;
import TP1.storeApplication.entity.LigneCommande;
import TP1.storeApplication.service.CommandeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

@Controller
@RequestMapping("/store")
public class CommandeController {

    private CommandeService commandeService;
    private KafkaTemplate<String, String> kafkaTemplate;

    public CommandeController(CommandeService commandeService,KafkaTemplate<String, String> kafkaTemplate) {
        this.commandeService = commandeService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/createCommande")
    public RedirectView createCommande (@RequestParam String nom, HttpSession session){
        Client clientConnecte = (Client) session.getAttribute("user");
        if (clientConnecte == null) {
            return new RedirectView("/store/home");
        }
        commandeService.createCommande(nom, clientConnecte);
        return new RedirectView("/store/user");
    }

    @GetMapping("/commande/{id}")
    public ModelAndView showCommande(@PathVariable Long id, HttpSession session) {
        Client clientConnecte = (Client) session.getAttribute("user");
        if (clientConnecte == null) {
            return new ModelAndView(new RedirectView("/store/home"));
        }
        Commande commande = commandeService.getCommandeById(id);
        if (commande == null || !commande.getClient().getEmail().equals(clientConnecte.getEmail())) {
            return new ModelAndView(new RedirectView("/store/user"));
        }
        return new ModelAndView("commande_detail", Map.of(
                "client", clientConnecte,
                "commande", commande
        ));
    }

    @GetMapping("/commande/imprimer/{id}")
    public ModelAndView imprimerCommande(@PathVariable Long id, HttpSession session) {
        Client clientConnecte = (Client) session.getAttribute("user");
        if (clientConnecte == null) {
            return new ModelAndView(new RedirectView("/store/home"));
        }
        Commande commande = commandeService.getCommandeById(id);
        if (commande == null || !commande.getClient().getEmail().equals(clientConnecte.getEmail())) {
            return new ModelAndView(new RedirectView("/store/user"));
        }
        return new ModelAndView("commande_imprimer", Map.of("commande", commande));
    }

    @PostMapping("/soumettreCommande")
    public RedirectView soumettreCommande(@RequestParam Long commandeId, HttpSession session) {
        Client clientConnecte = (Client) session.getAttribute("user");
        if (clientConnecte == null) return new RedirectView("/store/home");
        Commande commande = commandeService.getCommandeById(commandeId);
        if (commande != null && !commande.getLignes().isEmpty()) {
            for (LigneCommande ligne : commande.getLignes()) {
                String message = ligne.getLibelle() + ":" + ligne.getQuantite();
                kafkaTemplate.send("stock-topic", message);
            }
            String nomCmd = commande.getNom().replace(" ", "_");
            return new RedirectView("/store/user?valide=true&nom=" + nomCmd);
        }
        return new RedirectView("/store/user");
    }

}
