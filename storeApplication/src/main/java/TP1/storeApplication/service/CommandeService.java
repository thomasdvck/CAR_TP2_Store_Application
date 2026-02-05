package TP1.storeApplication.service;

import TP1.storeApplication.entity.Client;
import TP1.storeApplication.entity.Commande;
import TP1.storeApplication.entity.CommandeRepository;
import org.springframework.stereotype.Service;

@Service
public class CommandeService {

    private CommandeRepository commandeRepository;

    public CommandeService(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    public void createCommande(String nom, Client client) {
        Commande cmd = new Commande(nom, client);
        commandeRepository.save(cmd);
    }

    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }
}
