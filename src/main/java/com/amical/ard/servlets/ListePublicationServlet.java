
        package com.amical.ard.servlets;

import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.dao.CommentairePublicationDAO;
import com.amical.ard.dao.LikePublicationDAO;

import com.amical.ard.entites.Publication;
import com.amical.ard.entites.CommentairePublication;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.Utilisateur;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/liste-publications")
public class ListePublicationServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {

        emf =
                Persistence.createEntityManagerFactory(
                        "amicalePU"
                );
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em =
                emf.createEntityManager();

        try {

            PublicationDAO publicationDAO =
                    new PublicationDAO(em);

            CommentairePublicationDAO commentaireDAO =
                    new CommentairePublicationDAO(em);

            LikePublicationDAO likeDAO =
                    new LikePublicationDAO(em);

            HttpSession session =
                    request.getSession();

            Utilisateur adminConnecte =
                    (Utilisateur)
                            session.getAttribute(
                                    "utilisateurConnecte"
                            );

            Integer adminId = null;

            if(adminConnecte != null){

                adminId =
                        adminConnecte.getId();
            }

            // =========================
            // PUBLICATIONS
            // =========================

            List<Publication> publications =
                    publicationDAO.listerToutes();

            for (Publication publication : publications) {

                // =========================
                // AUTEUR PUBLICATION
                // =========================

                String auteurPublication =
                        publication.getAuteurPrenom()
                                + " "
                                + publication.getAuteurNom()
                                + " - "
                                + publication.getAuteurRole();

                request.setAttribute(
                        "auteur_publication_" +
                                publication.getId(),
                        auteurPublication
                );

                // =========================
                // PERMISSION SUPPRESSION
                // =========================

                boolean peutModifier = false;

                if(adminId != null
                        &&
                        publication.getAuteurId() != null){

                    peutModifier =
                            publication.getAuteurId()
                                    .equals(
                                            adminId.longValue()
                                    );
                }

                publication.setPeutModifier(
                        peutModifier
                );

                // =========================
                // COMMENTAIRES
                // =========================

                List<CommentairePublication> commentaires =
                        commentaireDAO.listerParPublication(
                                publication.getId()
                        );

                publication.setCommentaires(
                        commentaires
                );

                request.setAttribute(
                        "commentaires_" +
                                publication.getId(),
                        commentaires
                );

                // =========================
                // AUTEURS COMMENTAIRES
                // =========================

                for (CommentairePublication commentaire
                        : commentaires) {

                    String auteurNom =
                            "Utilisateur";

                    try {

                        Long utilisateurId =
                                commentaire.getUtilisateurId();

                        // ======================
                        // CHERCHER ADMIN
                        // ======================

                        Utilisateur utilisateur =
                                em.find(
                                        Utilisateur.class,
                                        utilisateurId.intValue()
                                );

                        if(utilisateur != null){

                            auteurNom =
                                    utilisateur.getPrenom()
                                            + " "
                                            + utilisateur.getNom();

                        } else {

                            // ======================
                            // CHERCHER ETUDIANT
                            // ======================

                            Etudiant etudiant =
                                    em.find(
                                            Etudiant.class,
                                            utilisateurId
                                    );

                            if(etudiant != null){

                                auteurNom =
                                        etudiant.getPrenom()
                                                + " "
                                                + etudiant.getNom();
                            }
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                    request.setAttribute(
                            "auteur_commentaire_" +
                                    commentaire.getId(),
                            auteurNom
                    );
                }

                // =========================
                // LIKES
                // =========================

                int totalLikes =
                        likeDAO.nombreLikes(
                                publication.getId()
                        );

                publication.setNombreLikes(
                        totalLikes
                );
            }

            request.setAttribute(
                    "publications",
                    publications
            );

            request.getRequestDispatcher(
                    "/pages/publications.jsp"
            ).forward(request, response);

        } finally {

            em.close();
        }
    }

    @Override
    public void destroy() {

        if (emf != null) {

            emf.close();
        }
    }
}

