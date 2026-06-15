
        package com.amical.ard.dao;

import com.amical.ard.entites.LikePublication;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

public class LikePublicationDAO {

    private EntityManager em;

    public LikePublicationDAO(EntityManager em) {

        this.em = em;
    }

    // =========================
    // AJOUTER LIKE
    // =========================

    public void ajouterLike(Long publicationId,
                            Long utilisateurId) {

        em.getTransaction().begin();

        LikePublication like =
                new LikePublication();

        like.setPublicationId(
                publicationId
        );

        like.setUtilisateurId(
                utilisateurId
        );

        like.setDateLike(
                LocalDateTime.now()
        );

        em.persist(like);

        em.getTransaction().commit();
    }

    // =========================
    // RETIRER LIKE
    // =========================

    public void retirerLike(Long publicationId,
                            Long utilisateurId) {

        em.getTransaction().begin();

        List<LikePublication> likes =
                em.createQuery(
                                "SELECT l FROM LikePublication l " +
                                        "WHERE l.publicationId = :p " +
                                        "AND l.utilisateurId = :u",
                                LikePublication.class
                        )
                        .setParameter("p", publicationId)
                        .setParameter("u", utilisateurId)
                        .getResultList();

        for (LikePublication l : likes) {

            em.remove(l);
        }

        em.getTransaction().commit();
    }

    // =========================
    // EXISTE DÉJÀ
    // =========================

    public boolean existeDeja(Long publicationId,
                              Long utilisateurId) {

        Long count =
                em.createQuery(
                                "SELECT COUNT(l) FROM LikePublication l " +
                                        "WHERE l.publicationId = :p " +
                                        "AND l.utilisateurId = :u",
                                Long.class
                        )
                        .setParameter("p", publicationId)
                        .setParameter("u", utilisateurId)
                        .getSingleResult();

        return count > 0;
    }

    // =========================
    // NOMBRE LIKES
    // =========================

    public int nombreLikes(Long publicationId) {

        Long count =
                em.createQuery(
                                "SELECT COUNT(l) FROM LikePublication l " +
                                        "WHERE l.publicationId = :p",
                                Long.class
                        )
                        .setParameter("p", publicationId)
                        .getSingleResult();

        return count.intValue();
    }
}

