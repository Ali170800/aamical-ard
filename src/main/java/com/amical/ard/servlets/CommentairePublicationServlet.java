package com.amical.ard.servlets;

import com.amical.ard.dao.CommentairePublicationDAO;
import com.amical.ard.entites.CommentairePublication;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/etudiant/commenter-publication")
public class CommentairePublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Indispensable pour les émojis
        request.setCharacterEncoding("UTF-8");

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            Long utilisateurId = null;
            Utilisateur admin = (Utilisateur) session.getAttribute("utilisateurConnecte");
            if (admin != null) {
                utilisateurId = admin.getId().longValue();
            } else {
                Etudiant etudiant = (Etudiant) session.getAttribute("etudiantConnecte");
                if (etudiant != null) {
                    utilisateurId = etudiant.getId();
                }
            }

            if (utilisateurId == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            String pubIdParam = request.getParameter("publicationId");
            String texte = request.getParameter("commentaire");

            if (pubIdParam == null || texte == null || texte.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/liste-publications");
                return;
            }

            CommentairePublication commentaire = new CommentairePublication();
            commentaire.setPublicationId(Long.parseLong(pubIdParam));
            commentaire.setUtilisateurId(utilisateurId);
            commentaire.setCommentaire(texte);
            commentaire.setDateCommentaire(LocalDateTime.now());

            // 2. Pas de begin()/commit() ici, le filtre le fait déjà
            CommentairePublicationDAO dao = new CommentairePublicationDAO(em);
            dao.ajouter(commentaire);

            response.sendRedirect(request.getContextPath() + "/liste-publications");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erreur COMMENTAIRE : " + e.getMessage());
        }
    }
}