package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.LogementEtudiantDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/supprimer-logement")
public class SupprimerLogementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {

            request.getSession().setAttribute(
                    "error",
                    "ID du logement manquant."
            );

            response.sendRedirect(
                    request.getContextPath() + "/liste-logements"
            );

            return;
        }

        Long logementId = Long.parseLong(idStr);

        EntityManager em = null;

        try {

            em = EntityManagerHelper.getEntityManager();

            LogementEtudiantDAO logementDAO =
                    new LogementEtudiantDAO(em);

            ActionLogDAO actionLogDAO =
                    new ActionLogDAO(em);

            // ==========================
            // UTILISATEUR CONNECTÉ
            // ==========================

            HttpSession session = request.getSession();

            Utilisateur utilisateurConnecte =
                    (Utilisateur) session.getAttribute("utilisateurConnecte");

            // ==========================
            // TRANSACTION
            // ==========================

            EntityManagerHelper.beginTransaction();

            // Suppression des paiements liés
            int paiementsSupprimes = em.createQuery(
                            "DELETE FROM PaiementLogement p " +
                                    "WHERE p.logementEtudiant.id = :logementId"
                    )
                    .setParameter("logementId", logementId)
                    .executeUpdate();

            // Suppression du logement
            logementDAO.supprimerParId(logementId);

            // ==========================
            // LOG D'ACTION
            // ==========================

            if (utilisateurConnecte != null) {

                String details =
                        "Logement ID=" + logementId;

                if (paiementsSupprimes > 0) {

                    details +=
                            " | " + paiementsSupprimes
                                    + " paiement(s) lié(s) supprimé(s)";
                }

                actionLogDAO.enregistrerAction(
                        utilisateurConnecte.getId(),
                        utilisateurConnecte.getPrenom()
                                + " "
                                + utilisateurConnecte.getNom(),
                        utilisateurConnecte.getRole(),
                        "Suppression de logement",
                        details
                );
            }

            EntityManagerHelper.commit();

            // ==========================
            // MESSAGE SUCCESS
            // ==========================

            String message =
                    "Logement supprimé avec succès !";

            if (paiementsSupprimes > 0) {

                message +=
                        " (" + paiementsSupprimes
                                + " paiement(s) supprimé(s))";
            }

            request.getSession().setAttribute(
                    "success",
                    message
            );

        } catch (Exception e) {

            EntityManagerHelper.rollback();

            e.printStackTrace();

            request.getSession().setAttribute(
                    "error",
                    "Impossible de supprimer ce logement : "
                            + e.getMessage()
            );

        } finally {

            if (em != null) {
                EntityManagerHelper.closeEntityManager();
            }
        }

        response.sendRedirect(
                request.getContextPath() + "/liste-logements"
        );
    }
}