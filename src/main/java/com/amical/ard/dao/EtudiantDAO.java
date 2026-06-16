package com.amical.ard.dao;

import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.LogementEtudiant;
import com.amical.ard.entites.PaiementLogement;
import jakarta.persistence.EntityManager;
import java.util.*;

public class EtudiantDAO {

    private final EntityManager em;

    public EtudiantDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Etudiant e) {
        if (em == null || e == null) return;
        em.persist(e);
    }

    public Etudiant trouverParId(Long id) {
        if (em == null || id == null) return null;
        return em.find(Etudiant.class, id);
    }

    public List<Etudiant> listerTous() {
        if (em == null) return Collections.emptyList();
        return em.createQuery("SELECT e FROM Etudiant e ORDER BY e.nom, e.prenom", Etudiant.class)
                .getResultList();
    }
    public List<Etudiant> findEtudiantsAvecLogement() {
        if (em == null) {
            System.err.println("ERREUR: EntityManager est null dans findEtudiantsAvecLogement()");
            return Collections.emptyList();
        }
        try {
            return em.createQuery(
                    "SELECT DISTINCT le.etudiant FROM LogementEtudiant le " +
                            "ORDER BY le.etudiant.nom, le.etudiant.prenom",
                    Etudiant.class
            ).getResultList();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des étudiants avec logement: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public List<Etudiant> rechercher(String id, String prenom, String telephone) {
        if (em == null) return Collections.emptyList();
        try {
            StringBuilder jpql = new StringBuilder("SELECT e FROM Etudiant e WHERE 1=1");
            Map<String, Object> params = new HashMap<>();

            if (id != null && !id.trim().isEmpty()) {
                jpql.append(" AND CAST(e.id AS string) LIKE :id");
                params.put("id", "%" + id.trim() + "%");
            }
            if (prenom != null && !prenom.trim().isEmpty()) {
                jpql.append(" AND LOWER(e.prenom) LIKE :prenom");
                params.put("prenom", "%" + prenom.trim().toLowerCase() + "%");
            }
            if (telephone != null && !telephone.trim().isEmpty()) {
                jpql.append(" AND e.telephone LIKE :telephone");
                params.put("telephone", "%" + telephone.trim() + "%");
            }

            jpql.append(" ORDER BY e.nom, e.prenom");

            var query = em.createQuery(jpql.toString(), Etudiant.class);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public Etudiant trouverParEmailEtMotDePasse(String email, String password) {
        if (em == null || email == null || password == null) return null;

        try {
            String cleanEmail = email.trim().toLowerCase();
            String cleanPassword = password.trim();   // Important : ne pas toucher au mot de passe (case sensitive)

            return em.createQuery(
                            "SELECT e FROM Etudiant e " +
                                    "WHERE LOWER(TRIM(e.email)) = :email " +
                                    "AND e.motDePasse = :password", Etudiant.class)
                    .setParameter("email", cleanEmail)
                    .setParameter("password", cleanPassword)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("❌ ÉCHEC CONNEXION pour email: " + email);
            return null;
        }
    }

    // ====================== MÉTHODES STATISTIQUES ======================

    public long compterTous() {
        if (em == null) return 0;
        try {
            return em.createQuery("SELECT COUNT(e) FROM Etudiant e", Long.class).getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
    public Etudiant trouverParEmail(String email) {

        if (em == null || email == null) {
            return null;
        }

        try {

            return em.createQuery(
                            "SELECT e FROM Etudiant e " +
                                    "WHERE LOWER(TRIM(e.email)) = :email",
                            Etudiant.class
                    )
                    .setParameter("email", email.toLowerCase().trim())
                    .getSingleResult();

        } catch (Exception e) {

            return null;
        }
    }
    public long compterParSexe(String sexe) {
        if (em == null || sexe == null) return 0;
        try {
            return em.createQuery("SELECT COUNT(e) FROM Etudiant e WHERE e.sexe = :sexe", Long.class)
                    .setParameter("sexe", sexe)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    public Map<String, Long> compterParFiliere() {
        Map<String, Long> map = new HashMap<>();
        if (em == null) return map;
        try {
            List<Object[]> results = em.createQuery(
                    "SELECT e.filiere, COUNT(e) FROM Etudiant e GROUP BY e.filiere",
                    Object[].class).getResultList();

            for (Object[] row : results) {
                String filiere = (String) row[0];
                Long count = (Long) row[1];
                if (filiere != null) map.put(filiere, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // ====================== MÉTHODES LOGEMENT & PAIEMENT ======================

    public LogementEtudiant getLogementByEtudiant(Long id) {
        if (em == null || id == null) return null;
        try {
            List<LogementEtudiant> list = em.createQuery(
                            "SELECT le FROM LogementEtudiant le WHERE le.etudiant.id = :id",
                            LogementEtudiant.class)
                    .setParameter("id", id)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PaiementLogement> getPaiementsByEtudiant(Long id) {
        if (em == null || id == null) return Collections.emptyList();
        try {
            return em.createQuery(
                            "SELECT p FROM PaiementLogement p WHERE p.logementEtudiant.etudiant.id = :id " +
                                    "ORDER BY p.annee DESC, p.mois DESC",
                            PaiementLogement.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /** ✅ Méthode demandée par AfficherFormulaireLogementServlet */
    public List<Etudiant> listerEtudiantsNonLogesParLocation(String location) {
        if (em == null) return Collections.emptyList();
        try {
            if (location == null || location.trim().isEmpty()) {
                return listerEtudiantsNonLoges();
            }
            return em.createQuery(
                            "SELECT e FROM Etudiant e WHERE e.adresse = :location " +
                                    "AND e.id NOT IN (SELECT le.etudiant.id FROM LogementEtudiant le) " +
                                    "ORDER BY e.nom, e.prenom",
                            Etudiant.class)
                    .setParameter("location", location.trim())
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Etudiant> listerEtudiantsNonLoges() {
        if (em == null) return Collections.emptyList();
        return em.createQuery(
                "SELECT e FROM Etudiant e WHERE e.id NOT IN (SELECT le.etudiant.id FROM LogementEtudiant le)",
                Etudiant.class).getResultList();
    }

    public List<Etudiant> listerEtudiantsLoges() {
        if (em == null) return Collections.emptyList();
        return em.createQuery(
                "SELECT DISTINCT le.etudiant FROM LogementEtudiant le ORDER BY le.etudiant.nom",
                Etudiant.class).getResultList();
    }

    public long compterEtudiantsLoges() {
        if (em == null) return 0;
        try {
            return em.createQuery("SELECT COUNT(DISTINCT le.etudiant) FROM LogementEtudiant le", Long.class)
                    .getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }
}