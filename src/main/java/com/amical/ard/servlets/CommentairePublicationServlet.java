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
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em =
                EntityManagerHelper.getEntityManager();

        try {

            HttpSession session =
                    request.getSession(false);

            Long utilisateurId = null;

            // =========================
            // ADMIN CONNECTÉ
            // =========================

            Utilisateur admin =
                    (Utilisateur)
                            session.getAttribute(
                                    "utilisateurConnecte"
                            );

            if (admin != null) {

                utilisateurId =
                        admin.getId().longValue();
            }

            // =========================
            // ETUDIANT CONNECTÉ
            // =========================

            if (utilisateurId == null) {

                Etudiant etudiant =
                        (Etudiant)
                                session.getAttribute(
                                        "etudiantConnecte"
                                );

                if (etudiant != null) {

                    utilisateurId =
                            etudiant.getId();
                }
            }

            // =========================
            // SÉCURITÉ
            // =========================

            if (utilisateurId == null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/login.jsp"
                );

                return;
            }

            // =========================
            // DONNÉES
            // =========================

            Long publicationId =
                    Long.parseLong(
                            request.getParameter(
                                    "publicationId"
                            )
                    );

            String texte =
                    request.getParameter(
                            "commentaire"
                    );

            // =========================
            // COMMENTAIRE
            // =========================

            CommentairePublication commentaire =
                    new CommentairePublication();

            commentaire.setPublicationId(
                    publicationId
            );

            commentaire.setUtilisateurId(
                    utilisateurId
            );

            commentaire.setCommentaire(
                    texte
            );

            commentaire.setDateCommentaire(
                    LocalDateTime.now()
            );

            // =========================
            // SAUVEGARDE
            // =========================

            CommentairePublicationDAO dao =
                    new CommentairePublicationDAO(em);

            dao.ajouter(commentaire);

            // =========================
            // REDIRECTION
            // =========================

            response.sendRedirect(
                    request.getContextPath()
                            + "/liste-publications"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Erreur COMMENTAIRE : "
                            + e.getMessage()
            );

        }
    }
}