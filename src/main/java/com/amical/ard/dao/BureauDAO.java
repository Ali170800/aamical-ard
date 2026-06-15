package com.amical.ard.dao;

import com.amical.ard.entites.Bureau;

// Conversion de javax → jakarta
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;

public class BureauDAO {
    private EntityManager em;

    public BureauDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Bureau b) {
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
    }

    public Bureau trouverParId(int id) {
        return em.find(Bureau.class, id);
    }

    public List<Bureau> listerTous() {
        return em.createQuery("SELECT b FROM Bureau b", Bureau.class).getResultList();
    }

    public void modifier(Bureau b) {
        em.getTransaction().begin();
        em.merge(b);
        em.getTransaction().commit();
    }

    public void supprimer(Bureau b) {
        em.getTransaction().begin();
        em.remove(em.contains(b) ? b : em.merge(b));
        em.getTransaction().commit();
    }

    public Bureau trouverParEmail(String email) {
        try {
            return em.createQuery("SELECT b FROM Bureau b WHERE b.email = :email", Bureau.class)
                     .setParameter("email", email)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<String> listerTousLesEmails() {
        return em.createQuery("SELECT b.email FROM Bureau b WHERE b.email IS NOT NULL", String.class)
                 .getResultList();
    }
}