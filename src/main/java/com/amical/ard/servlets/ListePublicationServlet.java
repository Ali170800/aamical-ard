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
        emf = Persistence.createEntityManagerFactory("amicalePU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();

        try {
            PublicationDAO publicationDAO = new PublicationDAO(em);
            CommentairePublicationDAO commentaireDAO = new CommentairePublicationDAO(em);
            LikePublicationDAO likeDAO = new LikePublicationDAO(em);

            HttpSession session = request.getSession();
            Utilisateur utilisateurConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
            Integer adminId = (utilisateurConnecte != null) ? utilisateurConnecte.getId() : null;

            List<Publication> publications = publicationDAO.listerToutes();

            for (Publication publication : publications) {

                // 1. Auteur et Rôle
                String nomAuteur = (publication.getAuteurPrenom() != null ? publication.getAuteurPrenom() : "") + " "
                        + (publication.getAuteurNom() != null ? publication.getAuteurNom() : "");
                String roleAuteur = (publication.getAuteurRole() != null) ? publication.getAuteurRole() : "Membre";

                request.setAttribute("auteur_publication_" + publication.getId(), nomAuteur);
                request.setAttribute("role_auteur_" + publication.getId(), roleAuteur);

                // 2. Permission de suppression (Vérification si auteur)
                boolean estAuteur = (adminId != null && publication.getAuteurId() != null
                        && publication.getAuteurId().equals(adminId.longValue()));
                request.setAttribute("est_auteur_" + publication.getId(), estAuteur);

                // 3. Commentaires
                List<CommentairePublication> commentaires = commentaireDAO.listerParPublication(publication.getId());
                publication.setCommentaires(commentaires);

                for (CommentairePublication commentaire : commentaires) {
                    String auteurNom = "Utilisateur";
                    try {
                        Long utilisateurId = commentaire.getUtilisateurId();
                        Utilisateur u = em.find(Utilisateur.class, utilisateurId.intValue());
                        if (u != null) {
                            auteurNom = u.getPrenom() + " " + u.getNom();
                        } else {
                            Etudiant e = em.find(Etudiant.class, utilisateurId);
                            if (e != null) auteurNom = e.getPrenom() + " " + e.getNom();
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                    request.setAttribute("auteur_commentaire_" + commentaire.getId(), auteurNom);
                }

                // 4. Likes
                publication.setNombreLikes(likeDAO.nombreLikes(publication.getId()));
            }

            request.setAttribute("publications", publications);
            request.getRequestDispatcher("/pages/publications.jsp").forward(request, response);

        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        if (emf != null) emf.close();
    }
}