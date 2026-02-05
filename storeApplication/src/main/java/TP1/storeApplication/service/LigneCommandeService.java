package TP1.storeApplication.service;

import TP1.storeApplication.entity.Commande;
import TP1.storeApplication.entity.LigneCommande;
import TP1.storeApplication.entity.LigneCommandeRepository;
import org.springframework.stereotype.Service;

@Service
public class LigneCommandeService {

    private LigneCommandeRepository ligneCommandeRepository;

    public LigneCommandeService(LigneCommandeRepository ligneCommandeRepository) {
        this.ligneCommandeRepository = ligneCommandeRepository;
    }

    public void ajouterLigne(String libelle, int quantite, double prix, Commande commande) {
        LigneCommande ligne = new LigneCommande(libelle, quantite, prix, commande);
        ligneCommandeRepository.save(ligne);
    }

    public void supprimerLigne(Long idLigne) {
        ligneCommandeRepository.deleteById(idLigne);
    }
}
