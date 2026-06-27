package com.amical.ard.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "utilisateur_id")
    private Long utilisateurId; // ID de l'étudiant/utilisateur qui reçoit la notif

    @Column(name = "message", length = 255)
    private String message;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "est_lu")
    private boolean estLu; // false par défaut

    // Constructeur vide
    public Notification() {
        this.dateCreation = LocalDateTime.now();
        this.estLu = false;
    }

    // Constructeur complet
    public Notification(Long utilisateurId, String message) {
        this.utilisateurId = utilisateurId;
        this.message = message;
        this.dateCreation = LocalDateTime.now();
        this.estLu = false;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public boolean isEstLu() { return estLu; }
    public void setEstLu(boolean estLu) { this.estLu = estLu; }
}