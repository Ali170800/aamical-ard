package com.amical.ard.entites;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "caravane")
public class Caravane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "caravane")
    private List<ParticipantCaravane> participants;

    // ✅ Champs temporaires pour l'affichage côté étudiant
    @Transient
    private boolean dejaInscrit = false;

    @Transient
    private String numeroChaise;

    // Constructeur vide
    public Caravane() {}

    // ====================== GETTERS & SETTERS ======================

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List<ParticipantCaravane> getParticipants() { return participants; }
    public void setParticipants(List<ParticipantCaravane> participants) { this.participants = participants; }

    // ==================== CHAMPS TEMPORAIRES ====================
    public boolean isDejaInscrit() { return dejaInscrit; }
    public void setDejaInscrit(boolean dejaInscrit) { this.dejaInscrit = dejaInscrit; }

    public String getNumeroChaise() { return numeroChaise; }
    public void setNumeroChaise(String numeroChaise) { this.numeroChaise = numeroChaise; }
}