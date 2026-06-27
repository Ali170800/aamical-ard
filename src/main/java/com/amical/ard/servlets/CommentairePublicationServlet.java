package com.amical.ard.servlets;

import com.amical.ard.dao.CommentairePublicationDAO;
import com.amical.ard.dao.NotificationDAO;
import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.entites.*;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/etudiant/commenter-publication")
public class CommentairePublicationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utilisateur admin = (Utilisateur) session.getAttribute("utilisateurConnecte");
        Etudiant etudiant = (Etudiant) session.getAttribute("etudiantConnecte");

        String nomAuteur = (admin != null) ? (admin.getPrenom() + " " + admin.getNom()) :
                (etudiant != null) ? (etudiant.getPrenom() + " " + etudiant.getNom()) : "Utilisateur";

        String texte = request.getParameter("commentaire");
        String pubIdStr = request.getParameter("publicationId");

        // Utilisation de l'EntityManager du Filtre
        EntityManager em = (EntityManager) request.getAttribute("em");

        if (texte != null && !texte.trim().isEmpty() && pubIdStr != null && em != null) {
            Long pubId = Long.parseLong(pubIdStr);

            // 1. Sauvegarde du commentaire
            CommentairePublication com = new CommentairePublication();
            com.setPublicationId(pubId);
            com.setCommentaire(texte.trim());
            com.setUtilisateurId(admin != null ? admin.getId().longValue() : etudiant.getId());
            com.setDateCommentaire(LocalDateTime.now());

            // On passe l'EM partagé
            new CommentairePublicationDAO(em).ajouter(com);

            // 2. Notification
            PublicationDAO pubDAO = new PublicationDAO(em);
            Publication publication = pubDAO.findById(pubId);

            if (publication != null && !publication.getAuteurId().equals(admin != null ? admin.getId().longValue() : etudiant.getId())) {
                String messageNotif = nomAuteur + " a commenté la publication de "
                        + publication.getAuteurPrenom() + " " + publication.getAuteurNom()
                        + " : \"" + texte + "\"";

                Notification n = new Notification();
                n.setUtilisateurId(publication.getAuteurId());
                n.setMessage(messageNotif);
                n.setDateCreation(LocalDateTime.now());
                n.setEstLu(false);

                // Utilisation du DAO avec l'EM du filtre
                new NotificationDAO(em).ajouter(n);
            }
        }

        // Nettoyage pour JSON
        String textePropre = texte.replace("\"", "'").replaceAll("[\\n\\r]", " ");
        String nomPropre = nomAuteur.replace("\"", "'");

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"nom\": \"" + nomPropre + "\", \"texte\": \"" + textePropre + "\"}");
    }
}