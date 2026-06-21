package com.amical.ard.servlets;

import com.amical.ard.dao.LikePublicationDAO;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/like-publication")
public class LikePublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Long utilisateurId = null;

        // Identification de l'utilisateur
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

        try {
            Long publicationId = Long.parseLong(request.getParameter("publicationId"));

            // Le filtre a déjà ouvert la transaction et l'EntityManager
            LikePublicationDAO dao = new LikePublicationDAO(EntityManagerHelper.getEntityManager());

            if (!dao.existeDeja(publicationId, utilisateurId)) {
                dao.ajouterLike(publicationId, utilisateurId);
            }

            int totalLikes = dao.nombreLikes(publicationId);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"nouveauTotal\": " + totalLikes + "}");

            // AUCUN em.close() ici : le filtre gérera la fermeture dans son 'finally'

        } catch (Exception e) {
            // En cas d'erreur, le filtre effectuera le rollback automatiquement
            throw new ServletException("Erreur lors du traitement du Like", e);
        }
    }
}