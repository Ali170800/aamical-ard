package com.amical.ard.servlets;

import com.amical.ard.dao.CommentairePublicationDAO;
import com.amical.ard.entites.*;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/etudiant/commenter-publication")
public class CommentairePublicationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            HttpSession session = request.getSession(false);
            Utilisateur admin = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;
            Etudiant etudiant = (session != null) ? (Etudiant) session.getAttribute("etudiantConnecte") : null;

            String auteurNom = (admin != null) ? (admin.getPrenom() + " " + admin.getNom()) :
                    (etudiant != null ? (etudiant.getPrenom() + " " + etudiant.getNom()) : "Utilisateur");
            Long utilisateurId = (admin != null) ? admin.getId().longValue() : (etudiant != null ? etudiant.getId() : null);

            if (utilisateurId == null) { response.sendError(403); return; }

            CommentairePublication c = new CommentairePublication();
            c.setPublicationId(Long.parseLong(request.getParameter("publicationId")));
            c.setUtilisateurId(utilisateurId);
            c.setCommentaire(request.getParameter("commentaire"));
            c.setDateCommentaire(LocalDateTime.now());

            new CommentairePublicationDAO(em).ajouter(c);

            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(String.format("{\"auteur\": \"%s\", \"texte\": \"%s\", \"date\": \"%s\"}",
                    auteurNom.replace("\"", "\\\""), c.getCommentaire().replace("\"", "\\\""),
                    c.getDateCommentaire().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        } finally { em.close(); }
    }
}