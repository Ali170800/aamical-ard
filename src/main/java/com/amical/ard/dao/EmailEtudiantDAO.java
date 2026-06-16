package com.amical.ard.dao;

import com.amical.ard.entites.EmailEtudiant;

// Conversion vers Jakarta
import jakarta.persistence.EntityManager;
import java.util.List;

public class EmailEtudiantDAO {
    private EntityManager em;

    public EmailEtudiantDAO(EntityManager em) {
        this.em = em;
    }

    // Ajout sans gestion de transaction ici (gérée par le code appelant)
    public void ajouter(EmailEtudiant e) {
        em.persist(e);
    }

    public EmailEtudiant trouverParId(int id) {
        return em.find(EmailEtudiant.class, id);
    }

    public List<EmailEtudiant> listerTous() {
        return em.createQuery("SELECT e FROM EmailEtudiant e", EmailEtudiant.class).getResultList();
    }

    // Modification sans transaction ici
    public void modifier(EmailEtudiant e) {
        em.merge(e);
    }

    // Suppression sans transaction ici
    public void supprimer(EmailEtudiant e) {
        em.remove(em.contains(e) ? e : em.merge(e));
    }
}