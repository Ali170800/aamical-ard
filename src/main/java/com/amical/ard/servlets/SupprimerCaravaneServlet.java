package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/caravane/supprimer")
public class SupprimerCaravaneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = null;

        try {

            int caravaneId =
                    Integer.parseInt(request.getParameter("id"));

            em = JpaUtil.getEntityManagerFactory()
                    .createEntityManager();

            CaravaneDAO caravaneDAO =
                    new CaravaneDAO(em);

            ParticipantCaravaneDAO participantDAO =
                    new ParticipantCaravaneDAO(em);

            ActionLogDAO actionLogDAO =
                    new ActionLogDAO(em);

            // ==========================
            // UTILISATEUR CONNECTÉ
            // ==========================

            HttpSession session =
                    request.getSession();

            Utilisateur utilisateurConnecte =
                    (Utilisateur) session.getAttribute(
                            "utilisateurConnecte"
                    );

            // ==========================
            // RÉCUPÉRATION CARAVANE
            // ==========================

            Caravane caravane =
                    caravaneDAO.trouverParId(caravaneId);

            if (caravane == null) {

                request.getSession().setAttribute(
                        "error",
                        "Caravane introuvable."
                );

                response.sendRedirect(
                        request.getContextPath()
                                + "/caravane/lister"
                );

                return;
            }

            String nomCaravane =
                    caravane.getNom();

            // ==========================
            // TRANSACTION
            // ==========================

            em.getTransaction().begin();

            // 1. Supprimer les participants liés

            int participantsSupprimes =
                    em.createQuery(
                                    "DELETE FROM ParticipantCaravane p WHERE p.caravane.id = :caravaneId"
                            )
                            .setParameter(
                                    "caravaneId",
                                    caravaneId
                            )
                            .executeUpdate();

            // 2. Supprimer la caravane

            caravaneDAO.supprimer(caravaneId);

            em.getTransaction().commit();

            // ==========================
            // LOG ACTION
            // ==========================

            if (utilisateurConnecte != null) {

                String details =
                        "Caravane ID="
                                + caravaneId
                                + " | Nom : "
                                + nomCaravane
                                + " | Participants supprimés : "
                                + participantsSupprimes;

                actionLogDAO.enregistrerAction(
                        utilisateurConnecte.getId(),
                        utilisateurConnecte.getPrenom()
                                + " "
                                + utilisateurConnecte.getNom(),
                        utilisateurConnecte.getRole(),
                        "Suppression caravane",
                        details
                );
            }

            request.getSession().setAttribute(
                    "success",
                    "La caravane et ses participants ont été supprimés avec succès."
            );

        } catch (Exception e) {

            e.printStackTrace();

            if (em != null
                    && em.getTransaction().isActive()) {

                em.getTransaction().rollback();
            }

            request.getSession().setAttribute(
                    "error",
                    "Erreur lors de la suppression de la caravane : "
                            + e.getMessage()
            );

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/caravane/lister"
        );
    }
}