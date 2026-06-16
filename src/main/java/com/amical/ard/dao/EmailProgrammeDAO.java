package com.amical.ard.dao;

import com.amical.ard.entites.EmailProgramme;

// Import Jakarta à la place de javax
import jakarta.persistence.EntityManager;
import java.util.List;

public class EmailProgrammeDAO {
    private EntityManager em;

    public EmailProgrammeDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(EmailProgramme e) {
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
    }

    public EmailProgramme trouverParId(int id) {
        return em.find(EmailProgramme.class, id);
    }

    public List<EmailProgramme> listerTous() {
        return em.createQuery("SELECT e FROM EmailProgramme e", EmailProgramme.class).getResultList();
    }

    public void modifier(EmailProgramme e) {
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }

    public void supprimer(EmailProgramme e) {
        em.getTransaction().begin();
        em.remove(em.contains(e) ? e : em.merge(e));
        em.getTransaction().commit();
    }
}