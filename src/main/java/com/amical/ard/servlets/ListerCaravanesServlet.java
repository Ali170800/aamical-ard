package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/caravane/lister")
public class ListerCaravanesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em =
                JpaUtil.getEntityManagerFactory()
                        .createEntityManager();

        try {

            CaravaneDAO caravaneDAO =
                    new CaravaneDAO(em);

            ActionLogDAO actionLogDAO =
                    new ActionLogDAO(em);

            // ==========================
            // UTILISATEUR CONNECTÉ
            // ==========================

            HttpSession session =
                    request.getSession(false);

            Utilisateur utilisateurConnecte =
                    session != null
                            ? (Utilisateur) session.getAttribute("utilisateurConnecte")
                            : null;

            // ==========================
            // LOG CONSULTATION
            // ==========================

            if (utilisateurConnecte != null) {

                actionLogDAO.enregistrerAction(
                        utilisateurConnecte.getId(),
                        utilisateurConnecte.getPrenom()
                                + " "
                                + utilisateurConnecte.getNom(),
                        utilisateurConnecte.getRole(),
                        "Consultation caravanes",
                        "Consultation de la liste des caravanes"
                );
            }

            // ==========================
            // RÉCUPÉRATION DONNÉES
            // ==========================

            List<Caravane> caravanes =
                    caravaneDAO.listerTous();

            request.setAttribute(
                    "caravanes",
                    caravanes
            );

            request.getRequestDispatcher(
                    "/pages/listerCaravanes.jsp"
            ).forward(request, response);

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}