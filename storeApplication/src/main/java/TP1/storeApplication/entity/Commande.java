package TP1.storeApplication.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Commande {

    @Id
    @GeneratedValue
    private Long id;

    private String nom;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "commande")
    private List<LigneCommande> lignes = new ArrayList<>();

    public Commande(String nom, Client client) {
        this.nom = nom;
        this.client = client;
    }

    public Commande() {
    }

    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public double getPrixTotalCommande(){
        double total=0;
        for(LigneCommande l : lignes){
            total += l.getPrixTotalLigne();
        }
        return total;
    }
}
