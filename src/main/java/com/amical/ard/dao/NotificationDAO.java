package com.amical.ard.dao;

import java.time.LocalDateTime;
import com.amical.ard.entites.Notification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class NotificationDAO {
    private EntityManager em;

    public NotificationDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Notification n) {
        em.getTransaction().begin();
        em.persist(n);
        em.getTransaction().commit();
    }

    public Long countNonLues(Long utilisateurId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(n) FROM Notification n WHERE n.utilisateurId = :uid AND n.estLu = false", Long.class);
        query.setParameter("uid", utilisateurId);
        return query.getSingleResult();
    }

    public List<Notification> getRecent(Long utilisateurId) {
        return em.createQuery("SELECT n FROM Notification n WHERE n.utilisateurId = :uid ORDER BY n.dateCreation DESC", Notification.class)
                .setParameter("uid", utilisateurId)
                .setMaxResults(5)
                .getResultList();
    }

    public List<Notification> listerPourEtudiant(Long etudiantId) {
        return em.createQuery("SELECT n FROM Notification n WHERE n.utilisateurId = :id ORDER BY n.dateCreation DESC", Notification.class)
                .setParameter("id", etudiantId)
                .getResultList();
    }

    public void persister(Notification n) {
        em.persist(n);
    }

    public void supprimerVieillesNotifications() {
        em.getTransaction().begin();
        // Suppression des notifications créées il y a plus de 24 heures
        em.createQuery("DELETE FROM Notification n WHERE n.dateCreation < :seuil")
                .setParameter("seuil", LocalDateTime.now().minusHours(24))
                .executeUpdate();
        em.getTransaction().commit();
    }
}