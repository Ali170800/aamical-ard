package com.amical.ard.dao;

import com.amical.ard.entites.Appartement;

import jakarta.persistence.EntityManager;
import java.util.List;

public class AppartementDAO {
    private EntityManager em;

    public AppartementDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Appartement a) {
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
    }

    // ✅ Correction ici : Integer au lieu de Long
    public Appartement trouverParId(Integer id) {
        return em.find(Appartement.class, id);
    }

    public List<Appartement> listerTous() {
        return em.createQuery("SELECT a FROM Appartement a", Appartement.class).getResultList();
    }

    public void modifier(Appartement a) {
        em.getTransaction().begin();
        em.merge(a);
        em.getTransaction().commit();
    }
    public void supprimer(Long id) {
        em.getTransaction().begin();
        Appartement a = em.find(Appartement.class, id);
        if (a != null) {
            em.remove(a);
        }
        em.getTransaction().commit();
    }


    public void mettreAJour(Appartement appartement) {
        em.getTransaction().begin();
        em.merge(appartement);
        em.getTransaction().commit();
    }
    public void supprimer(Appartement a) {
        em.getTransaction().begin();
        em.remove(em.contains(a) ? a : em.merge(a));
        em.getTransaction().commit();
    }
}