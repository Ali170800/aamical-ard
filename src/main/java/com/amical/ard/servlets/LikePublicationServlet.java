package com.amical.ard.servlets;

import com.amical.ard.dao.LikePublicationDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/like-publication")
public class LikePublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Le filtre gère l'EntityManager, on le récupère simplement
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            HttpSession session = request.getSession(false);
            Long utilisateurId = null;

            // Récupération de l'utilisateur
            Utilisateur admin = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;
            if (admin != null) {
                utilisateurId = admin.getId().longValue();
            } else {
                Etudiant etudiant = (session != null) ? (Etudiant) session.getAttribute("etudiantConnecte") : null;
                if (etudiant != null) {
                    utilisateurId = etudiant.getId();
                }
            }

            // Sécurité : retour erreur si non connecté
            if (utilisateurId == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Long publicationId = Long.parseLong(request.getParameter("publicationId"));
            LikePublicationDAO dao = new LikePublicationDAO(em);

            // Ajout du like
            if (!dao.existeDeja(publicationId, utilisateurId)) {
                dao.ajouterLike(publicationId, utilisateurId);
            }

            // Calculer le nouveau total
            int totalLikes = dao.nombreLikes(publicationId);

            // RÉPONSE JSON POUR AJAX (au lieu de sendRedirect)
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"nouveauTotal\": " + totalLikes + "}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
        // Pas de em.close() ici, le filtre le fera pour vous.
    }
}