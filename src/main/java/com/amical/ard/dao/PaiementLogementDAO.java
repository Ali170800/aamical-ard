package com.amical.ard.dao;

import com.amical.ard.entites.PaiementLogement;
import com.amical.ard.enums.StatutPaiement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;

public class PaiementLogementDAO {

    private EntityManager em;

    public PaiementLogementDAO(EntityManager em) {
        this.em = em;
    }

    // =========================
    // ➕ AJOUTER UN PAIEMENT
    // =========================
    public void ajouter(PaiementLogement p) {

        try {

            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            em.persist(p);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

            throw new RuntimeException(
                    "Erreur lors de l'ajout du paiement",
                    e
            );
        }
    }

    // =========================
    // ✅ VÉRIFIER SI PAIEMENT EXISTE
    // =========================
    public boolean existePaiement(Long etudiantId,
                                  int mois,
                                  int annee) {

        try {

            Long count = em.createQuery(
                            "SELECT COUNT(p) " +
                                    "FROM PaiementLogement p " +
                                    "WHERE p.logementEtudiant.etudiant.id = :etudiantId " +
                                    "AND p.mois = :mois " +
                                    "AND p.annee = :annee",
                            Long.class
                    )
                    .setParameter("etudiantId", etudiantId)
                    .setParameter("mois", mois)
                    .setParameter("annee", annee)
                    .getSingleResult();

            return count > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // ✅ VÉRIFIER DOUBLON EN MODIFICATION
    // =========================
    public boolean existePaiementPourModification(int paiementId,
                                                  Long etudiantId,
                                                  int mois,
                                                  int annee) {

        try {

            Long count = em.createQuery(
                            "SELECT COUNT(p) " +
                                    "FROM PaiementLogement p " +
                                    "WHERE p.logementEtudiant.etudiant.id = :etudiantId " +
                                    "AND p.mois = :mois " +
                                    "AND p.annee = :annee " +
                                    "AND p.id <> :paiementId",
                            Long.class
                    )
                    .setParameter("etudiantId", etudiantId)
                    .setParameter("mois", mois)
                    .setParameter("annee", annee)
                    .setParameter("paiementId", paiementId)
                    .getSingleResult();

            return count > 0;

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // 🔍 RECHERCHE PAR CRITÈRES
    // =========================
    public List<PaiementLogement> rechercherParCritere(Integer mois,
                                                       Integer annee,
                                                       String statutStr) {

        StringBuilder jpql = new StringBuilder(
                "SELECT p FROM PaiementLogement p " +
                        "JOIN FETCH p.logementEtudiant le " +
                        "JOIN FETCH le.etudiant " +
                        "JOIN FETCH le.appartement " +
                        "WHERE 1=1"
        );

        if (mois != null) {
            jpql.append(" AND p.mois = :mois");
        }

        if (annee != null) {
            jpql.append(" AND p.annee = :annee");
        }

        if (statutStr != null && !statutStr.isEmpty()) {
            jpql.append(" AND p.statut = :statut");
        }

        TypedQuery<PaiementLogement> query =
                em.createQuery(jpql.toString(), PaiementLogement.class);

        if (mois != null) {
            query.setParameter("mois", mois);
        }

        if (annee != null) {
            query.setParameter("annee", annee);
        }

        if (statutStr != null && !statutStr.isEmpty()) {

            StatutPaiement statut =
                    StatutPaiement.valueOf(statutStr);

            query.setParameter("statut", statut);
        }

        return query.getResultList();
    }

    // =========================
    // 🔍 TROUVER PAR ID ÉTUDIANT
    // =========================
    public List<PaiementLogement> findByEtudiantId(Long etudiantId) {

        return em.createQuery(
                        "SELECT p FROM PaiementLogement p " +
                                "JOIN FETCH p.logementEtudiant le " +
                                "JOIN FETCH le.etudiant e " +
                                "JOIN FETCH le.appartement a " +
                                "WHERE e.id = :etudiantId",
                        PaiementLogement.class
                )
                .setParameter("etudiantId", etudiantId)
                .getResultList();
    }

    // =========================
    // 📃 LISTER AVEC DÉTAILS
    // =========================
    public List<PaiementLogement> lister() {

        return em.createQuery(
                """
                SELECT p FROM PaiementLogement p
                JOIN FETCH p.logementEtudiant le
                JOIN FETCH le.etudiant
                JOIN FETCH le.appartement
                """,
                PaiementLogement.class
        ).getResultList();
    }

    // =========================
    // 📃 LISTER PAR ÉTUDIANT
    // =========================
    public List<PaiementLogement> listerParEtudiant(Long idEtudiant) {

        return em.createQuery(
                        "SELECT p FROM PaiementLogement p " +
                                "WHERE p.logementEtudiant.etudiant.id = :id",
                        PaiementLogement.class
                )
                .setParameter("id", idEtudiant)
                .getResultList();
    }

    // =========================
    // 📊 COMPTER IMPAYÉS
    // =========================
    public long compterEtudiantsLogesImpayesMoisActuel() {

        int mois = java.time.LocalDate.now().getMonthValue();
        int annee = java.time.LocalDate.now().getYear();

        return em.createQuery(
                        "SELECT COUNT(DISTINCT le.etudiant.id) " +
                                "FROM LogementEtudiant le " +
                                "WHERE le.etudiant.id NOT IN (" +
                                "SELECT p.logementEtudiant.etudiant.id " +
                                "FROM PaiementLogement p " +
                                "WHERE p.mois = :mois " +
                                "AND p.annee = :annee " +
                                "AND p.statut = :paye" +
                                ")",
                        Long.class
                )
                .setParameter("mois", mois)
                .setParameter("annee", annee)
                .setParameter("paye", StatutPaiement.PAYE)
                .getSingleResult();
    }

    // =========================
    // 🔍 TROUVER PAR ID
    // =========================
    public PaiementLogement trouverParId(int id) {

        return em.find(PaiementLogement.class, id);
    }

    // =========================
    // 🔍 RECHERCHE MOIS/ANNÉE
    // =========================
    public List<PaiementLogement> rechercherParMoisEtAnnee(int mois,
                                                           int annee) {

        return em.createQuery(
                        "SELECT p FROM PaiementLogement p " +
                                "WHERE p.mois = :mois " +
                                "AND p.annee = :annee",
                        PaiementLogement.class
                )
                .setParameter("mois", mois)
                .setParameter("annee", annee)
                .getResultList();
    }

    // =========================
    // 📃 LISTER TOUS
    // =========================
    public List<PaiementLogement> listerTous() {

        return em.createQuery(
                        "SELECT p FROM PaiementLogement p",
                        PaiementLogement.class
                )
                .getResultList();
    }

    // =========================
    // 🔄 METTRE À JOUR
    // =========================
    public void mettreAJour(PaiementLogement paiement) {

        try {

            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            em.merge(paiement);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

            throw new RuntimeException(
                    "Erreur lors de la mise à jour",
                    e
            );
        }
    }

    // =========================
    // ✏ MODIFIER
    // =========================
    public void modifier(PaiementLogement p) {

        try {

            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            em.merge(p);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

            throw new RuntimeException(
                    "Erreur lors de la modification du paiement",
                    e
            );
        }
    }

    // =========================
    // ❌ SUPPRIMER
    // =========================
    public void supprimer(PaiementLogement p) {

        try {

            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
            }

            if (!em.contains(p)) {
                p = em.merge(p);
            }

            em.remove(p);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

            throw new RuntimeException(
                    "Erreur lors de la suppression du paiement",
                    e
            );
        }
    }

    // =========================
    // 📋 DÉTAILS IMPAYÉS
    // =========================
    public List<Map> listerEtudiantsImpayesDetailsMoisActuel() {

        int mois = java.time.LocalDate.now().getMonthValue();
        int annee = java.time.LocalDate.now().getYear();

        String jpql =
                """
                SELECT NEW map(
                    e.prenom as prenom,
                    e.nom as nom,
                    a.numero as appartement
                )
                FROM LogementEtudiant le
                JOIN le.etudiant e
                JOIN le.appartement a
                WHERE le.etudiant.id NOT IN (
                    SELECT p.logementEtudiant.etudiant.id
                    FROM PaiementLogement p
                    WHERE p.mois = :mois
                    AND p.annee = :annee
                    AND p.statut = 'PAYE'
                )
                ORDER BY e.nom, e.prenom
                """;

        return em.createQuery(jpql, Map.class)
                .setParameter("mois", mois)
                .setParameter("annee", annee)
                .getResultList();
    }
}