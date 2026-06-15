package com.amical.ard.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "logement_etudiant")
public class LogementEtudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "appartement_id", nullable = false)
    private Appartement appartement;

    public LogementEtudiant() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Appartement getAppartement() {
        return appartement;
    }

    public void setAppartement(Appartement appartement) {
        this.appartement = appartement;
    }

    @Override
    public String toString() {
        return "LogementEtudiant{" +
                "id=" + id +
                ", etudiant=" + (etudiant != null ? etudiant.getPrenom() + " " + etudiant.getNom() : "null") +
                ", appartement=" + (appartement != null ? appartement.getNomAppartement() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogementEtudiant)) return false;
        return id != null && id.equals(((LogementEtudiant) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}