package TP1.storeApplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LigneCommande {
    @Id
    @GeneratedValue
    private Long id;

    private String libelle;
    private int quantite;
    private double prixUnitaire;

    @ManyToOne
    private Commande commande;

    public LigneCommande(String libelle, int quantite, double prixUnitaire, Commande commande) {
        this.libelle = libelle;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.commande = commande;
    }

    public LigneCommande(){
    }

    public Long getId() {
        return id;
    }
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public double getPrixTotalLigne(){
        return this.quantite*this.prixUnitaire;
    }
}
