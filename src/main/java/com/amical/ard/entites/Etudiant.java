package com.amical.ard.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "etudiant")
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String sexe;
    private String filiere;
    private String niveau;

    @Column(name = "annee_universitaire")
    private String anneeUniversitaire;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "numero_urgence")
    private String numeroUrgence;

    private String pathologie;
    private String adresse;

    @Column(name = "photo_profil")
    private String photoProfil;

    private String email;

    @ManyToOne
    @JoinColumn(name = "appartement_id")
    private Appartement appartement;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @Column(name = "date_inscription")
    private java.time.LocalDateTime dateInscription;

    @Column(name = "statut")
    private String statut = "INACTIF";   // ACTIF, INACTIF, BLOQUE

    @Column(name = "code_validation")
    private String codeValidation;

    // ==================== CONSTRUCTEUR ====================
    public Etudiant() {}

    // ==================== GETTERS & SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public String getAnneeUniversitaire() { return anneeUniversitaire; }
    public void setAnneeUniversitaire(String anneeUniversitaire) {
        this.anneeUniversitaire = anneeUniversitaire;
    }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getNumeroUrgence() { return numeroUrgence; }
    public void setNumeroUrgence(String numeroUrgence) {
        this.numeroUrgence = numeroUrgence;
    }

    public String getPathologie() { return pathologie; }
    public void setPathologie(String pathologie) { this.pathologie = pathologie; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getPhotoProfil() { return photoProfil; }
    public void setPhotoProfil(String photoProfil) { this.photoProfil = photoProfil; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Appartement getAppartement() { return appartement; }
    public void setAppartement(Appartement appartement) { this.appartement = appartement; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public java.time.LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(java.time.LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getCodeValidation() { return codeValidation; }
    public void setCodeValidation(String codeValidation) { this.codeValidation = codeValidation; }
}