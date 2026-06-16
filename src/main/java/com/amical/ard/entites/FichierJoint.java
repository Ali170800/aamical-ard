package com.amical.ard.entites;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "fichier_joint")
public class FichierJoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // =========================================
    // NOM PHYSIQUE DU FICHIER
    // =========================================

    @Column(name = "nom_fichier", nullable = false)
    private String nomFichier;

    // =========================================
    // TITRE PERSONNALISÉ
    // =========================================

    @Column(name = "titre", nullable = false)
    private String titre;

    // =========================================
    // CHEMIN PHYSIQUE
    // =========================================

    @Column(name = "chemin_fichier", nullable = false)
    private String cheminFichier;

    // =========================================
    // TYPE DOCUMENT
    // =========================================

    @Enumerated(EnumType.STRING)
    @Column(name = "type_fichier")
    private TypeFichier typeFichier;

    // =========================================
    // DATE UPLOAD
    // =========================================

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_upload")
    private Date dateUpload;

    // =========================================
    // AUTEUR
    // =========================================

    @Column(name = "nom_auteur")
    private String nomAuteur;

    @Column(name = "role_auteur")
    private String roleAuteur;

    // =========================================
    // RELATIONS
    // =========================================

    @ManyToOne
    @JoinColumn(name = "id_reunion")
    private Reunion reunion;

    @ManyToOne
    @JoinColumn(name = "id_caravane")
    private Caravane caravane;

    // =========================================
    // CONSTRUCTEUR VIDE
    // =========================================

    public FichierJoint() {
    }

    // =========================================
    // GETTERS & SETTERS
    // =========================================

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public TypeFichier getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(TypeFichier typeFichier) {
        this.typeFichier = typeFichier;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    public String getNomAuteur() {
        return nomAuteur;
    }

    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }

    public String getRoleAuteur() {
        return roleAuteur;
    }

    public void setRoleAuteur(String roleAuteur) {
        this.roleAuteur = roleAuteur;
    }

    public Reunion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion reunion) {
        this.reunion = reunion;
    }

    public Caravane getCaravane() {
        return caravane;
    }

    public void setCaravane(Caravane caravane) {
        this.caravane = caravane;
    }

    // =========================================
    // ENUM TYPE FICHIER
    // =========================================

    public enum TypeFichier {

        Compte_Rendu("Compte-Rendu"),

        Bilan_Caravane("Bilan Caravane"),

        Fiche_Activite("Fiche Activité"),

        Autre("Autre");

        private final String label;

        TypeFichier(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}