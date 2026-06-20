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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Le filtre ouvre l'EntityManager, nous le récupérons ici
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            HttpSession session = request.getSession(false);
            Long utilisateurId = null;

            // Récupération de l'ID utilisateur (Admin ou Etudiant)
            Utilisateur admin = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;
            if (admin != null) {
                utilisateurId = admin.getId().longValue();
            } else {
                Etudiant etudiant = (session != null) ? (Etudiant) session.getAttribute("etudiantConnecte") : null;
                if (etudiant != null) {
                    utilisateurId = etudiant.getId();
                }
            }

            // Vérification sécurité
            if (utilisateurId == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            // Récupération paramètres
            Long publicationId = Long.parseLong(request.getParameter("publicationId"));
            String texte = request.getParameter("commentaire");

            // Création objet commentaire
            CommentairePublication commentaire = new CommentairePublication();
            commentaire.setPublicationId(publicationId);
            commentaire.setUtilisateurId(utilisateurId);
            commentaire.setCommentaire(texte);
            commentaire.setDateCommentaire(LocalDateTime.now());

            // Sauvegarde avec gestion de transaction
            em.getTransaction().begin(); // Démarrage de la transaction
            CommentairePublicationDAO dao = new CommentairePublicationDAO(em);
            dao.ajouter(commentaire);
            em.getTransaction().commit(); // Validation des données

            // Redirection finale
            response.sendRedirect(request.getContextPath() + "/liste-publications");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Annulation en cas d'erreur
            }
            e.printStackTrace();
            response.getWriter().println("Erreur COMMENTAIRE : " + e.getMessage());
        }
        // Le filtre se chargera de fermer la connexion (em.close()) ici
    }
}