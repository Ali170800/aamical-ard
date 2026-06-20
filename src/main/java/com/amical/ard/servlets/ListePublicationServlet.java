package com.amical.ard.servlets;

import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.dao.CommentairePublicationDAO;
import com.amical.ard.dao.LikePublicationDAO;
import com.amical.ard.entites.Publication;
import com.amical.ard.entites.CommentairePublication;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
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

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Le filtre gère désormais l'ouverture et la fermeture de l'EntityManager
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            PublicationDAO publicationDAO = new PublicationDAO(em);
            CommentairePublicationDAO commentaireDAO = new CommentairePublicationDAO(em);
            LikePublicationDAO likeDAO = new LikePublicationDAO(em);

            HttpSession session = request.getSession();
            Utilisateur adminConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");

            Integer adminId = (adminConnecte != null) ? adminConnecte.getId() : null;

            List<Publication> publications = publicationDAO.listerToutes();

            for (Publication publication : publications) {
                // Auteur publication
                String auteurPublication = publication.getAuteurPrenom() + " "
                        + publication.getAuteurNom() + " - "
                        + publication.getAuteurRole();
                request.setAttribute("auteur_publication_" + publication.getId(), auteurPublication);

                // Permission suppression
                boolean peutModifier = (adminId != null && publication.getAuteurId() != null)
                        && publication.getAuteurId().equals(adminId.longValue());
                publication.setPeutModifier(peutModifier);

                // Commentaires
                List<CommentairePublication> commentaires = commentaireDAO.listerParPublication(publication.getId());
                publication.setCommentaires(commentaires);
                request.setAttribute("commentaires_" + publication.getId(), commentaires);

                // Auteurs commentaires
                for (CommentairePublication commentaire : commentaires) {
                    String auteurNom = "Utilisateur";
                    try {
                        Long utilisateurId = commentaire.getUtilisateurId();
                        Utilisateur utilisateur = em.find(Utilisateur.class, utilisateurId.intValue());
                        if (utilisateur != null) {
                            auteurNom = utilisateur.getPrenom() + " " + utilisateur.getNom();
                        } else {
                            Etudiant etudiant = em.find(Etudiant.class, utilisateurId);
                            if (etudiant != null) {
                                auteurNom = etudiant.getPrenom() + " " + etudiant.getNom();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    request.setAttribute("auteur_commentaire_" + commentaire.getId(), auteurNom);
                }

                // Likes
                int totalLikes = likeDAO.nombreLikes(publication.getId());
                publication.setNombreLikes(totalLikes);
            }

            request.setAttribute("publications", publications);
            request.getRequestDispatcher("/pages/publications.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du chargement des publications.");
        }
        // PAS DE finally { em.close(); } ou destroy() ici : le filtre s'en occupe.
    }
}