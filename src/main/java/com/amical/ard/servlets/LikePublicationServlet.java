package com.amical.ard.servlets;

import com.amical.ard.dao.LikePublicationDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/like-publication")
public class LikePublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            HttpSession session = request.getSession(false);
            Long utilisateurId = null;

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
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Long publicationId = Long.parseLong(request.getParameter("publicationId"));
            LikePublicationDAO dao = new LikePublicationDAO(em);

            // 1. Pas de begin()/commit() ici
            if (!dao.existeDeja(publicationId, utilisateurId)) {
                dao.ajouterLike(publicationId, utilisateurId);
            }

            int totalLikes = dao.nombreLikes(publicationId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"nouveauTotal\": " + totalLikes + "}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erreur technique\"}");
        }
    }
}