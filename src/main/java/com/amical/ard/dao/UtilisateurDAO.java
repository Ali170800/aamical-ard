package com.amical.ard.dao;

import com.amical.ard.entites.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class UtilisateurDAO {
    private EntityManager em;

    public UtilisateurDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Utilisateur u) {
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    public Utilisateur trouverParId(Integer id) {
        return em.find(Utilisateur.class, id);
    }

    public Utilisateur trouverParLogin(String login) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.login = :login", Utilisateur.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /** ✅ NOUVELLE MÉTHODE IMPORTANTE */
    public Utilisateur trouverParEmail(String email) {
        try {
            return em.createQuery(
                            "SELECT u FROM Utilisateur u WHERE LOWER(TRIM(u.email)) = :email", Utilisateur.class)
                    .setParameter("email", email.toLowerCase().trim())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Utilisateur> listerTous() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    public void modifier(Utilisateur u) {
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
    }

    public void supprimer(Utilisateur u) {
        em.getTransaction().begin();
        em.remove(em.contains(u) ? u : em.merge(u));
        em.getTransaction().commit();
    }
}