package com.amical.ard.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "action_log")
public class ActionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "utilisateur_id")
    private Integer utilisateurId;

    @Column(name = "nom_utilisateur", length = 100)
    private String nomUtilisateur;

    @Column(length = 50)
    private String role;

    @Column(nullable = false, length = 255)
    private String action;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(name = "date_action")
    private LocalDateTime dateAction;

    // Constructeurs
    public ActionLog() {}

    public ActionLog(Integer utilisateurId, String nomUtilisateur, String role,
                     String action, String details) {
        this.utilisateurId = utilisateurId;
        this.nomUtilisateur = nomUtilisateur;
        this.role = role;
        this.action = action;
        this.details = details;
        this.dateAction = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Integer utilisateurId) { this.utilisateurId = utilisateurId; }

    public String getNomUtilisateur() { return nomUtilisateur; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getDateAction() { return dateAction; }
    public void setDateAction(LocalDateTime dateAction) { this.dateAction = dateAction; }
}