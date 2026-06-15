package com.amical.ard.entites;

import com.amical.ard.enums.StatutPaiement;
import jakarta.persistence.*;

@Entity
@Table(name = "paiementlogement")
public class PaiementLogement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "logement_etudiant_id", referencedColumnName = "id")
    private LogementEtudiant logementEtudiant;

    private double montant;

    private int mois;

    private int annee;

    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;

    // ✅ Supprimé : private LocalDate datePaiement;

    // ======= Getters et Setters =======

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LogementEtudiant getLogementEtudiant() {
        return logementEtudiant;
    }

    public void setLogementEtudiant(LogementEtudiant logementEtudiant) {
        this.logementEtudiant = logementEtudiant;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public StatutPaiement getStatut() {
        return statut;
    }

    public void setStatut(StatutPaiement statut) {
        this.statut = statut;
    }

    // Utilitaires
    public Etudiant getEtudiant() {
        return (logementEtudiant != null) ? logementEtudiant.getEtudiant() : null;
    }

    public Appartement getAppartement() {
        return (logementEtudiant != null) ? logementEtudiant.getAppartement() : null;
    }
}