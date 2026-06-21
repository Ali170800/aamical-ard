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

        // Récupération de l'EntityManager géré par le filtre (ThreadLocal)
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            Long utilisateurId = null;

            // Tentative de récupération Admin
            Utilisateur admin = (Utilisateur) session.getAttribute("utilisateurConnecte");
            if (admin != null) {
                utilisateurId = admin.getId().longValue();
            } else {
                // Tentative de récupération Etudiant
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

            Long publicationId = Long.parseLong(pubIdParam);

            // Création du commentaire
            CommentairePublication commentaire = new CommentairePublication();
            commentaire.setPublicationId(publicationId);
            commentaire.setUtilisateurId(utilisateurId);
            commentaire.setCommentaire(texte);
            commentaire.setDateCommentaire(LocalDateTime.now());

            // Sauvegarde via le DAO
            CommentairePublicationDAO dao = new CommentairePublicationDAO(em);

            // Note: Assurez-vous que votre dao.ajouter() utilise em.persist()
            // et que la transaction est gérée par le filtre ou ici par em.getTransaction()
            em.getTransaction().begin();
            dao.ajouter(commentaire);
            em.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/liste-publications");

        } catch (Exception e) {
            // Rollback en cas d'erreur si une transaction est active
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            response.getWriter().println("Erreur COMMENTAIRE : " + e.getMessage());
        }
        // PAS DE em.close() ! Le filtre s'en occupe.
    }
}