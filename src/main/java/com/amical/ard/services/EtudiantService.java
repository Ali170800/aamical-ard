package com.amical.ard.services;

import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.LogementEtudiant;
import jakarta.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EtudiantService {

    private EntityManager em;

    public EtudiantService(EntityManager em) {
        this.em = em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public Etudiant trouverParId(Long id) {
        if (em == null) {
            System.err.println("ERREUR: EntityManager est null dans trouverParId()");
            return null;
        }
        try {
            return em.find(Etudiant.class, id);
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche de l'étudiant ID=" + id + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Suppression COMPLÈTE d'un étudiant :
     * - Supprime tous ses paiements
     * - Supprime son logement (s'il existe)
     * - Supprime l'étudiant
     */
    public boolean supprimerEtudiant(Long id) {
        if (em == null) {
            System.err.println("ERREUR: EntityManager est null dans supprimerEtudiant()");
            return false;
        }

        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            // 1. Supprimer tous les paiements liés à cet étudiant
            int paiementsSupprimes = em.createQuery(
                            "DELETE FROM PaiementLogement p WHERE p.logementEtudiant.etudiant.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            // 2. Supprimer le logement de cet étudiant (s'il existe)
            int logementsSupprimes = em.createQuery(
                            "DELETE FROM LogementEtudiant le WHERE le.etudiant.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            // 3. Supprimer l'étudiant lui-même
            Etudiant etudiant = em.find(Etudiant.class, id);
            if (etudiant != null) {
                em.remove(etudiant);
            }

            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }

            System.out.println("✓ Suppression complète réussie pour étudiant ID=" + id
                    + " | Paiements supprimés: " + paiementsSupprimes
                    + " | Logements supprimés: " + logementsSupprimes);

            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erreur lors de la suppression complète de l'étudiant ID=" + id
                    + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ====================== AUTRES MÉTHODES (inchangées) ======================

    public List<Etudiant> recupererTousLesEtudiants() {
        if (em == null) return Collections.emptyList();
        try {
            return em.createQuery("SELECT e FROM Etudiant e", Etudiant.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Etudiant> recupererEtudiantsLoges() {
        if (em == null) return Collections.emptyList();
        try {
            List<LogementEtudiant> logements = em.createQuery(
                    "SELECT l FROM LogementEtudiant l WHERE l.etudiant IS NOT NULL",
                    LogementEtudiant.class
            ).getResultList();

            return logements.stream()
                    .map(LogementEtudiant::getEtudiant)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean creerEtudiant(Etudiant etudiant) {
        if (em == null || etudiant == null) return false;
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }
            em.persist(etudiant);
            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mise à jour d'un étudiant - Version stable
     */
    public boolean mettreAJourEtudiant(Etudiant etudiant) {
        if (em == null || etudiant == null) {
            System.err.println("ERREUR: EntityManager ou Etudiant est null dans mettreAJourEtudiant()");
            return false;
        }
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            em.merge(etudiant);

            if (em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erreur lors de la mise à jour de l'étudiant ID=" +
                    (etudiant.getId() != null ? etudiant.getId() : "null") + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}