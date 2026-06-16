package com.amical.ard.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "login"),
                @UniqueConstraint(columnNames = "email")
        })
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String login;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "mot_de_passe", length = 100)
    private String motDePasse;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(length = 50)
    private String prenom;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(nullable = false, length = 20)
    private String statut = "INACTIF";

    @Column(name = "code_validation", length = 10)
    private String codeValidation;

    // GETTERS / SETTERS
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getCodeValidation() { return codeValidation; }
    public void setCodeValidation(String codeValidation) { this.codeValidation = codeValidation; }
}