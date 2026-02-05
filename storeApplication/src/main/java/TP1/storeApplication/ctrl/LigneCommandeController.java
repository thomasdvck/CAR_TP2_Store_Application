package TP1.storeApplication.ctrl;

import TP1.storeApplication.entity.Client;
import TP1.storeApplication.entity.Commande;
import TP1.storeApplication.service.CommandeService;
import TP1.storeApplication.service.LigneCommandeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/store")
public class LigneCommandeController {

    private LigneCommandeService ligneCommandeService;
    private CommandeService commandeService;

    public LigneCommandeController(LigneCommandeService ligneCommandeService, CommandeService commandeService){
        this.ligneCommandeService = ligneCommandeService;
        this.commandeService = commandeService;
    }

    @PostMapping("/createLigne")
    public RedirectView createLigne(@RequestParam Long commandeId, @RequestParam String libelle, @RequestParam int quantite, @RequestParam double prixUnitaire, HttpSession session){
        Client clientConnecte = (Client) session.getAttribute("user");
        if (clientConnecte == null) {
            return new RedirectView("/store/home");
        }
        Commande commande = commandeService.getCommandeById(commandeId);
        if (commande == null || !commande.getClient().getEmail().equals(clientConnecte.getEmail())) {
            return new RedirectView("/store/user");
        }
        ligneCommandeService.ajouterLigne(libelle, quantite, prixUnitaire, commande);
        return new RedirectView("/store/commande/" + commandeId);
    }

    @GetMapping("/deleteLigne")
    public RedirectView deleteLigne(@RequestParam Long id, @RequestParam Long commandeId,HttpSession session) {
        Client clientConnecte = (Client) session.getAttribute("user");
        if (clientConnecte == null) {
            return new RedirectView("/store/home");
        }
        Commande commande = commandeService.getCommandeById(commandeId);
        if (commande == null || !commande.getClient().getEmail().equals(clientConnecte.getEmail())) {
            return new RedirectView("/store/user");
        }
        ligneCommandeService.supprimerLigne(id);
        return new RedirectView("/store/commande/" + commandeId);
    }
}
