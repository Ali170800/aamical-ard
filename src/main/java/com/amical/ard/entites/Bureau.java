package com.amical.ard.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "bureau")
public class Bureau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(name = "role_bureau", nullable = false)
    private String roleBureau;

    private String numero;

    @Column(length = 255)
    private String email; // ✅ Nouveau champ ajouté

    @Column(name = "annee_mandat")
    private String anneeMandat;

    // Constructeur vide
    public Bureau() {}

    // Getters et Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRoleBureau() {
        return roleBureau;
    }

    public void setRoleBureau(String roleBureau) {
        this.roleBureau = roleBureau;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAnneeMandat() {
        return anneeMandat;
    }

    public void setAnneeMandat(String anneeMandat) {
        this.anneeMandat = anneeMandat;
    }
}