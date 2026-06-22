package com.amical.ard.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "election")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private String poste;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalDateTime dateCreation; // Ajouté pour cohérence avec le Servlet

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidatElection> candidats = new ArrayList<>();

    // --- CHAMPS TRANSIENTS POUR LE DASHBOARD (Non persistés en base) ---
    @Transient
    private long nbVotes;

    @Transient
    private long totalEtudiants;

    @Transient
    private double tauxParticipation;

    public Election() {}

    // Getters et Setters existants
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }
    public void setDateFin(LocalDateTime dateFin) { this.dateFin = dateFin; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public List<CandidatElection> getCandidats() { return candidats; }
    public void setCandidats(List<CandidatElection> candidats) { this.candidats = candidats; }

    // --- Getters et Setters pour les statistiques ---
    public long getNbVotes() { return nbVotes; }
    public void setNbVotes(long nbVotes) { this.nbVotes = nbVotes; }

    public long getTotalEtudiants() { return totalEtudiants; }
    public void setTotalEtudiants(long totalEtudiants) { this.totalEtudiants = totalEtudiants; }

    public double getTauxParticipation() { return tauxParticipation; }
    public void setTauxParticipation(double tauxParticipation) { this.tauxParticipation = tauxParticipation; }
}