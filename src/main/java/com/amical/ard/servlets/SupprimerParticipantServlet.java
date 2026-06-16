package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/participant/supprimer")
public class SupprimerParticipantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = null;

        try {

            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            em = JpaUtil.getEntityManagerFactory()
                    .createEntityManager();

            ParticipantCaravaneDAO participantDAO =
                    new ParticipantCaravaneDAO(em);

            ParticipantCaravane participant =
                    participantDAO.trouverParId(id);

            if (participant == null) {

                request.getSession().setAttribute(
                        "error",
                        "Participant introuvable."
                );

                response.sendRedirect(
                        request.getContextPath()
                                + "/caravane/lister"
                );

                return;
            }

            // ==========================
            // INFOS AVANT SUPPRESSION
            // ==========================

            int caravaneId =
                    participant.getCaravane().getId();

            String nomComplet =
                    participant.getPrenom()
                            + " "
                            + participant.getNom();

            // ==========================
            // TRANSACTION
            // ==========================

            em.getTransaction().begin();

            participantDAO.supprimer(participant);

            // ==========================
            // LOG ACTION
            // ==========================

            HttpSession session =
                    request.getSession();

            Utilisateur utilisateur =
                    (Utilisateur) session.getAttribute(
                            "utilisateurConnecte"
                    );

            if (utilisateur != null) {

                ActionLogDAO actionLogDAO =
                        new ActionLogDAO(em);

                actionLogDAO.enregistrerAction(
                        utilisateur.getId(),
                        utilisateur.getPrenom()
                                + " "
                                + utilisateur.getNom(),
                        utilisateur.getRole(),
                        "Suppression participant caravane",
                        "Participant supprimé : "
                                + nomComplet
                );
            }

            em.getTransaction().commit();

            request.getSession().setAttribute(
                    "success",
                    "Participant supprimé avec succès."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/participants/caravane?id="
                            + caravaneId
            );

        } catch (Exception e) {

            e.printStackTrace();

            if (em != null
                    && em.getTransaction().isActive()) {

                em.getTransaction().rollback();
            }

            request.getSession().setAttribute(
                    "error",
                    "Erreur lors de la suppression : "
                            + e.getMessage()
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/caravane/lister"
            );

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}