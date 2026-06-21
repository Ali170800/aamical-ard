package com.amical.ard.servlets;

import com.amical.ard.dao.CommentairePublicationDAO;
import com.amical.ard.entites.CommentairePublication;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EntityManagerHelper;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Long utilisateurId = null;

        // --- Logique d'authentification ---
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
            response.sendRedirect(request.getContextPath() + "/pages/connexionEtudiant.jsp");
            return;
        }

        // --- Logique métier ---
        try {
            Long publicationId = Long.parseLong(request.getParameter("publicationId"));
            String texte = request.getParameter("commentaire");

            if (texte == null || texte.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/liste-publications");
                return;
            }

            CommentairePublication commentaire = new CommentairePublication();
            commentaire.setPublicationId(publicationId);
            commentaire.setUtilisateurId(utilisateurId);
            commentaire.setCommentaire(texte.trim());
            commentaire.setDateCommentaire(LocalDateTime.now());

            // On récupère l'EntityManager déjà actif grâce au Filtre
            CommentairePublicationDAO dao = new CommentairePublicationDAO(EntityManagerHelper.getEntityManager());
            dao.ajouter(commentaire);

            response.sendRedirect(request.getContextPath() + "/liste-publications");

        } catch (Exception e) {
            // Si une erreur survient, le Filtre fera le rollback automatiquement
            throw new ServletException("Erreur lors de l'ajout du commentaire", e);
        }
        // PAS DE finally { em.close() } ici ! Le filtre s'en occupe.
    }
}