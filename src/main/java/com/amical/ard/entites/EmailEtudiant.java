package com.amical.ard.entites;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "email_etudiant")
public class EmailEtudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_email_programme")
    private EmailProgramme emailProgramme;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_etudiant")
    private Etudiant etudiant;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_lu", nullable = true)
    private StatutLu statutLu = StatutLu.NonLu;

    @Column(name = "date_envoi_effective")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnvoiEffective;

    // Constructeur vide
    public EmailEtudiant() {}

    // Getters et setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmailProgramme getEmailProgramme() {
        return emailProgramme;
    }

    public void setEmailProgramme(EmailProgramme emailProgramme) {
        this.emailProgramme = emailProgramme;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public StatutLu getStatutLu() {
        return statutLu;
    }

    public void setStatutLu(StatutLu statutLu) {
        this.statutLu = statutLu;
    }

    public Date getDateEnvoiEffective() {
        return dateEnvoiEffective;
    }

    public void setDateEnvoiEffective(Date dateEnvoiEffective) {
        this.dateEnvoiEffective = dateEnvoiEffective;
    }

    // Enum simplifiée
    public enum StatutLu {
        NonLu,
        Lu
    }
}