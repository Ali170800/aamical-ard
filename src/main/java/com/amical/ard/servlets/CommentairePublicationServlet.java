package com.amical.ard.servlets;

import com.amical.ard.dao.CommentairePublicationDAO;
import com.amical.ard.entites.CommentairePublication;
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
import java.time.LocalDateTime;

@WebServlet("/etudiant/commenter-publication")
public class CommentairePublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Long publicationId = Long.parseLong(request.getParameter("publicationId"));
            String texte = request.getParameter("commentaire");

            CommentairePublication commentaire = new CommentairePublication();
            commentaire.setPublicationId(publicationId);
            commentaire.setUtilisateurId(utilisateurId);
            commentaire.setCommentaire(texte);
            commentaire.setDateCommentaire(LocalDateTime.now());

            CommentairePublicationDAO dao = new CommentairePublicationDAO(em);
            dao.ajouter(commentaire);

            // Réponse JSON silencieuse
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"status\": \"success\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\"}");
        }
    }
}