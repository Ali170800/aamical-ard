package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.enums.StatutPaiement;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/participant/modifier")
public class ModifierParticipantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        EntityManager em =
                JpaUtil.getEntityManagerFactory()
                        .createEntityManager();

        try {

            ParticipantCaravaneDAO participantDAO =
                    new ParticipantCaravaneDAO(em);

            ParticipantCaravane participant =
                    participantDAO.trouverParId(id);

            if (participant == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Participant introuvable !"
                );

                return;
            }

            request.setAttribute("participant", participant);

            request.getRequestDispatcher(
                    "/pages/modifierParticipant.jsp"
            ).forward(request, response);

        } finally {

            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em =
                JpaUtil.getEntityManagerFactory()
                        .createEntityManager();

        try {

            int id =
                    Integer.parseInt(request.getParameter("id"));

            String statut =
                    request.getParameter("statut");

            ParticipantCaravaneDAO participantDAO =
                    new ParticipantCaravaneDAO(em);

            ParticipantCaravane participant =
                    participantDAO.trouverParId(id);

            if (participant == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Participant introuvable !"
                );

                return;
            }

            // 🔥 IMPORTANT :
            // récupérer caravaneId AVANT modification

            int caravaneId =
                    participant.getCaravane().getId();

            // ==============================
            // MODIFICATION
            // ==============================

            em.getTransaction().begin();

            if ("PAYE".equalsIgnoreCase(statut)) {

                participant.setStatutPaiement(
                        StatutPaiement.PAYE
                );

                participant.setMontantPaye(
                        participant.getMontant().intValue()
                );

            } else {

                participant.setStatutPaiement(
                        StatutPaiement.Non_Paye
                );

                participant.setMontantPaye(0);
            }

            participantDAO.modifier(participant);

            // ==============================
            // LOG
            // ==============================

            HttpSession session =
                    request.getSession(false);

            Utilisateur utilisateur =
                    (Utilisateur) session.getAttribute(
                            "utilisateurConnecte"
                    );

            if (utilisateur != null) {

                ActionLogDAO logDAO =
                        new ActionLogDAO(em);

                logDAO.enregistrerAction(
                        utilisateur.getId(),
                        utilisateur.getPrenom()
                                + " "
                                + utilisateur.getNom(),
                        utilisateur.getRole(),
                        "Modification participant caravane",
                        "Participant : "
                                + participant.getNom()
                                + " "
                                + participant.getPrenom()
                                + " | Nouveau statut : "
                                + statut
                );
            }

            em.getTransaction().commit();

            // ✅ REDIRECTION DIRECTE
            // vers liste des participants

            response.sendRedirect(
                    request.getContextPath()
                            + "/participants/caravane?id="
                            + caravaneId
            );

        } catch (Exception e) {

            e.printStackTrace();

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            request.setAttribute(
                    "erreur",
                    "Erreur lors de la modification."
            );

            request.getRequestDispatcher(
                    "/pages/modifierParticipant.jsp"
            ).forward(request, response);

        } finally {

            em.close();
        }
    }
}