package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/etudiant/caravanes")
public class ListeCaravanesEtudiantServle extends HttpServlet {   // ← Nom corrigé

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Etudiant etudiantConnecte = (session != null)
                ? (Etudiant) session.getAttribute("etudiantConnecte")
                : null;

        if (etudiantConnecte == null) {
            response.sendRedirect(request.getContextPath() + "/pages/connexionEtudiant.jsp");
            return;
        }

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            CaravaneDAO caravaneDAO = new CaravaneDAO(em);
            ParticipantCaravaneDAO participantDAO = new ParticipantCaravaneDAO(em);
            ActionLogDAO actionLogDAO = new ActionLogDAO(em);

            // Log de consultation
            actionLogDAO.enregistrerAction(
                    null,
                    etudiantConnecte.getPrenom() + " " + etudiantConnecte.getNom(),
                    "ETUDIANT",
                    "Consultation caravanes",
                    "Consultation de la liste des caravanes disponibles"
            );

            List<Caravane> caravanes = caravaneDAO.listerTous();

            for (Caravane caravane : caravanes) {
                // Vérifier si l'étudiant est déjà inscrit
                ParticipantCaravane participant = participantDAO.trouverParEmailEtCaravane(
                        etudiantConnecte.getEmail(),
                        caravane.getId().longValue()
                );

                boolean dejaInscrit = participant != null;
                caravane.setDejaInscrit(dejaInscrit);

                // Numéro de chaise si déjà inscrit
                if (dejaInscrit && participant.getNumeroChaise() != null) {
                    caravane.setNumeroChaise(participant.getNumeroChaise());
                }
            }

            request.setAttribute("caravanes", caravanes);
            request.setAttribute("etudiant", etudiantConnecte);   // Utile pour le JSP

            request.getRequestDispatcher("/pages/ListeCaravanesEtudiant.jsp")
                    .forward(request, response);

        } finally {
            em.close();
        }
    }
}