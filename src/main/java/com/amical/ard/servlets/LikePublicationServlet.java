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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            HttpSession session = request.getSession(false);
            Long utilisateurId = null;

            // Récupération de l'utilisateur (Admin ou Etudiant)
            Utilisateur admin = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;
            if (admin != null) {
                utilisateurId = admin.getId().longValue();
            } else {
                Etudiant etudiant = (session != null) ? (Etudiant) session.getAttribute("etudiantConnecte") : null;
                if (etudiant != null) {
                    utilisateurId = etudiant.getId();
                }
            }

            if (utilisateurId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"status\": \"error\", \"message\": \"Non connecté\"}");
                return;
            }

            Long publicationId = Long.parseLong(request.getParameter("publicationId"));
            LikePublicationDAO dao = new LikePublicationDAO(em);

            if (!dao.existeDeja(publicationId, utilisateurId)) {
                dao.ajouterLike(publicationId, utilisateurId);
            }

            // Réponse JSON silencieuse : le navigateur ne change pas de page
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\": \"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"" + e.getMessage() + "\"}");
        }
    }
}