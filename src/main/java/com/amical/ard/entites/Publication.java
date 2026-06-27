package com.amical.ard.entites;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "publication")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image = ""; // Initialisé à chaîne vide
    private String video = ""; // Initialisé à chaîne vide
    private String typeMedia;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "auteur_id")
    private Long auteurId;

    @Column(name = "auteur_type")
    private String auteurType;

    @Column(name = "auteur_nom")
    private String auteurNom;

    @Column(name = "auteur_prenom")
    private String auteurPrenom;

    @Column(name = "auteur_role")
    private String auteurRole;

    @Column(name = "date_publication")
    private LocalDateTime datePublication;

    @Transient
    private List<CommentairePublication> commentaires;

    @Transient
    private int nombreLikes;

    @Transient
    private boolean peutModifier;

    public Publication() {
        this.datePublication = LocalDateTime.now();
        this.image = "";
        this.video = "";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getVideo() { return video; }
    public void setVideo(String video) { this.video = video; }
    public String getTypeMedia() { return typeMedia; }
    public void setTypeMedia(String typeMedia) { this.typeMedia = typeMedia; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getAuteurId() { return auteurId; }
    public void setAuteurId(Long auteurId) { this.auteurId = auteurId; }
    public String getAuteurType() { return auteurType; }
    public void setAuteurType(String auteurType) { this.auteurType = auteurType; }
    public String getAuteurNom() { return auteurNom; }
    public void setAuteurNom(String auteurNom) { this.auteurNom = auteurNom; }
    public String getAuteurPrenom() { return auteurPrenom; }
    public void setAuteurPrenom(String auteurPrenom) { this.auteurPrenom = auteurPrenom; }
    public String getAuteurRole() { return auteurRole; }
    public void setAuteurRole(String auteurRole) { this.auteurRole = auteurRole; }
    public LocalDateTime getDatePublication() { return datePublication; }
    public void setDatePublication(LocalDateTime datePublication) { this.datePublication = datePublication; }
    public List<CommentairePublication> getCommentaires() { return commentaires; }
    public void setCommentaires(List<CommentairePublication> commentaires) { this.commentaires = commentaires; }
    public int getNombreLikes() { return nombreLikes; }
    public void setNombreLikes(int nombreLikes) { this.nombreLikes = nombreLikes; }
    public boolean isPeutModifier() { return peutModifier; }
    public void setPeutModifier(boolean peutModifier) { this.peutModifier = peutModifier; }
}