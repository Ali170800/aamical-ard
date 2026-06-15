package com.amical.ard.dao;

import com.amical.ard.entites.CommentairePublication;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CommentairePublicationDAO {

    private EntityManager em;

    public CommentairePublicationDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(CommentairePublication commentaire) {

        em.getTransaction().begin();
        em.persist(commentaire);
        em.getTransaction().commit();
    }

    public List<CommentairePublication> lister(Long publicationId) {

        return em.createQuery(
                        "SELECT c FROM CommentairePublication c WHERE c.publicationId=:p ORDER BY c.dateCommentaire DESC",
                        CommentairePublication.class
                )
                .setParameter("p", publicationId)
                .getResultList();
    }
    public List<CommentairePublication> listerParPublication(Long publicationId) {

        return em.createQuery(
                        "SELECT c FROM CommentairePublication c " +
                                "WHERE c.publicationId = :id " +
                                "ORDER BY c.dateCommentaire DESC",
                        CommentairePublication.class
                )
                .setParameter("id", publicationId)
                .getResultList();
    }
    public void supprimer(Long id) {

        CommentairePublication c =
                em.find(CommentairePublication.class, id);

        if (c != null) {

            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
        }
    }
}