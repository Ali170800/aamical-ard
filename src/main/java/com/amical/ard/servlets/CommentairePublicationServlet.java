package com.amical.ard.servlets;

import com.amical.ard.dao.CommentairePublicationDAO;
import com.amical.ard.entites.*;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/etudiant/commenter-publication")
public class CommentairePublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            HttpSession session = request.getSession(false);
            Utilisateur admin = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;
            Etudiant etudiant = (session != null) ? (Etudiant) session.getAttribute("etudiantConnecte") : null;

            Long utilisateurId = (admin != null) ? admin.getId().longValue() : (etudiant != null ? etudiant.getId() : null);
            String auteurNom = (admin != null) ? (admin.getPrenom() + " " + admin.getNom()) : (etudiant != null ? (etudiant.getPrenom() + " " + etudiant.getNom()) : "Utilisateur");

            if (utilisateurId == null) { response.sendError(403); return; }

            Long publicationId = Long.parseLong(request.getParameter("publicationId"));
            String texte = request.getParameter("commentaire");

            CommentairePublication commentaire = new CommentairePublication();
            commentaire.setPublicationId(publicationId);
            commentaire.setUtilisateurId(utilisateurId);
            commentaire.setCommentaire(texte);
            commentaire.setDateCommentaire(LocalDateTime.now());

            em.getTransaction().begin();
            new CommentairePublicationDAO(em).ajouter(commentaire);
            em.getTransaction().commit();

            // Réponse JSON pour le front-end
            response.setContentType("application/json");
            response.getWriter().write(String.format(
                    "{\"auteur\": \"%s\", \"texte\": \"%s\", \"date\": \"%s\"}",
                    auteurNom, texte.replace("\"", "\\\""), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            ));

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            response.sendError(500);
        }
    }
}