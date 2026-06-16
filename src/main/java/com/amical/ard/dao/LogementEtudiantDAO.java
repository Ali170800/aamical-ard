package com.amical.ard.dao;

import com.amical.ard.entites.LogementEtudiant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

public class LogementEtudiantDAO {

    private EntityManager em;

    public LogementEtudiantDAO(EntityManager em) {
        this.em = em;
    }

    // ====================== AJOUT ======================
    public void ajouter(LogementEtudiant l) {
        if (l == null) {
            throw new IllegalArgumentException("Le logement ne peut pas être null");
        }

        try {
            // On ne commence une transaction QUE si aucune n'est active
            // Cela évite les conflits avec EntityManagerHelper
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            em.persist(l);

            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de l'ajout du logement", e);
        }
    }

    // ====================== RECHERCHE ======================
    public LogementEtudiant trouverParEtudiantId(Long etudiantId) {
        try {
            return em.createQuery(
                            "SELECT le FROM LogementEtudiant le WHERE le.etudiant.id = :id",
                            LogementEtudiant.class)
                    .setParameter("id", etudiantId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;   // Important : retourne null si aucun résultat
        } catch (Exception e) {
            // On logge les autres erreurs potentielles
            e.printStackTrace();
            return null;
        }
    }

    public LogementEtudiant trouverParId(Long id) {
        return em.find(LogementEtudiant.class, id);
    }

    public List<LogementEtudiant> listerTous() {
        return em.createQuery(
                        "SELECT l FROM LogementEtudiant l",
                        LogementEtudiant.class)
                .getResultList();
    }

    // ====================== SUPPRESSION ======================
    /**
     * Suppression par ID - Version robuste
     */
    public void supprimerParId(Long id) {
        LogementEtudiant logement = em.find(LogementEtudiant.class, id);
        if (logement != null) {
            try {
                if (!em.getTransaction().isActive()) {
                    em.getTransaction().begin();
                }
                if (!em.contains(logement)) {
                    logement = em.merge(logement);
                }
                em.remove(logement);
                if (em.getTransaction().isActive()) {
                    em.getTransaction().commit();
                }
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw new RuntimeException("Erreur lors de la suppression du logement", e);
            }
        }
    }

    /**
     * Suppression d'une entité LogementEtudiant
     */
    public void supprimer(LogementEtudiant l) {
        if (l != null) {
            try {
                if (!em.getTransaction().isActive()) {
                    em.getTransaction().begin();
                }
                if (!em.contains(l)) {
                    l = em.merge(l);
                }
                em.remove(l);
                if (em.getTransaction().isActive()) {
                    em.getTransaction().commit();
                }
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw new RuntimeException("Erreur lors de la suppression du logement", e);
            }
        }
    }

    // ====================== MODIFICATION ======================
    public void modifier(LogementEtudiant l) {
        if (l == null) return;

        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.merge(l);
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la modification du logement", e);
        }
    }
}