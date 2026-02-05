package TP1.storeApplication.ctrl;

import TP1.storeApplication.entity.Client;
import TP1.storeApplication.service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping("/store")
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping("/home")
    public ModelAndView home(HttpSession session){
        if(session.getAttribute("user") != null){
            return new ModelAndView(new RedirectView("/store/user"));
        }else {
            return new ModelAndView("login_register");
        }
    }

    @GetMapping("/user")
    public ModelAndView user(HttpSession session){
        Client clientConnecte = (Client) session.getAttribute("user");
        if(clientConnecte == null){
            return new ModelAndView(new RedirectView("/store/home"));
        }
        Client clientDonneAjour = clientService.getClientByEmail(clientConnecte.getEmail());
        return new ModelAndView("store_user", Map.of("client", clientDonneAjour));
    }

    @PostMapping("register")
    public RedirectView register(@RequestParam String email, @RequestParam String password, @RequestParam String nom, @RequestParam String prenom){
        clientService.createClient(email,password,nom,prenom);
        return new RedirectView("/store/home");
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String email, @RequestParam String password, HttpSession session){
        Client client = clientService.connexion(email, password);
        if(client != null){
            session.setAttribute("user",client);
            return new RedirectView("/store/user");
        }
        return new RedirectView("/store/home");
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/store/home");
    }
}
