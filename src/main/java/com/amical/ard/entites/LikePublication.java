package com.amical.ard.entites;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "like_publication")
public class LikePublication {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publication_id")
    private Long publicationId;

    @Column(name = "utilisateur_id")
    private Long utilisateurId;

    @Column(name = "date_like")
    private LocalDateTime dateLike;

    public LikePublication() {

        this.dateLike = LocalDateTime.now();
    }

// =========================
// ID
// =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

// =========================
// PUBLICATION ID
// =========================

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

// =========================
// UTILISATEUR ID
// =========================

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

// =========================
// DATE LIKE
// =========================

    public LocalDateTime getDateLike() {
        return dateLike;
    }

    public void setDateLike(LocalDateTime dateLike) {
        this.dateLike = dateLike;
    }


}
