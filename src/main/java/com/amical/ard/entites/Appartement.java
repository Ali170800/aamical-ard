package com.amical.ard.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "appartement")
public class Appartement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom_appartement", nullable = false)
    private String nomAppartement;

    @Column(columnDefinition = "TEXT")
    private String description;

    // ✅ AJOUT IMPORTANT
    @Column(name = "adresse")
    private String adresse;

    // Constructeur vide
    public Appartement() {}

    // Getters et Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomAppartement() {
        return nomAppartement;
    }

    public void setNomAppartement(String nomAppartement) {
        this.nomAppartement = nomAppartement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // ✅ GETTER / SETTER AJOUTÉS
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}