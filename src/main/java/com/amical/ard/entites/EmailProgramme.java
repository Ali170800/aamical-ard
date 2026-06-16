package com.amical.ard.entites;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "email_programme")
public class EmailProgramme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String sujet;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_envoi", nullable = false)
    private Date dateEnvoi;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_email")
    private TypeEmail typeEmail = TypeEmail.Autre;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_envoi")
    private StatutEnvoi statutEnvoi = StatutEnvoi.EnAttente;

    // Constructeur vide
    public EmailProgramme() {}

    // Getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public TypeEmail getTypeEmail() {
        return typeEmail;
    }

    public void setTypeEmail(TypeEmail typeEmail) {
        this.typeEmail = typeEmail;
    }

    public StatutEnvoi getStatutEnvoi() {
        return statutEnvoi;
    }

    public void setStatutEnvoi(StatutEnvoi statutEnvoi) {
        this.statutEnvoi = statutEnvoi;
    }

    // ✅ Enums propres (sans label, sans toString)
    public enum TypeEmail {
        RappelLoyer,
        AnnonceCaravane,
        Autre
    }

    public enum StatutEnvoi {
        EnAttente,
        Envoye
    }
}