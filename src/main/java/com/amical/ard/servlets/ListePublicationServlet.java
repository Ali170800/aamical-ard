package com.amical.ard.servlets;

import com.amical.ard.dao.*;
import com.amical.ard.entites.*;
import com.amical.ard.utils.EntityManagerHelper; // Votre Helper qui récupère l'EM du filtre
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/liste-publications")
public class ListePublicationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Le filtre a déjà ouvert l'EntityManager et l'a lié au Thread actuel
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            PublicationDAO publicationDAO = new PublicationDAO(em);
            CommentairePublicationDAO commentaireDAO = new CommentairePublicationDAO(em);
            LikePublicationDAO likeDAO = new LikePublicationDAO(em);

            HttpSession session = request.getSession();
            Utilisateur adminConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
            Long adminId = (adminConnecte != null) ? adminConnecte.getId().longValue() : null;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            List<Publication> publications = publicationDAO.listerToutes();

            for (Publication publication : publications) {
                // Info Auteur
                String auteurPublication = publication.getAuteurPrenom() + " " + publication.getAuteurNom();
                request.setAttribute("auteur_publication_" + publication.getId(), auteurPublication);
                request.setAttribute("role_auteur_" + publication.getId(), publication.getAuteurRole());

                // Date de publication
                String datePub = (publication.getDatePublication() != null) ? publication.getDatePublication().format(dtf) : "Date inconnue";
                request.setAttribute("date_publication_" + publication.getId(), datePub);

                // Permissions
                boolean peutModifier = (adminId != null && publication.getAuteurId() != null)
                        && publication.getAuteurId().equals(adminId);
                publication.setPeutModifier(peutModifier);

                // Commentaires
                List<CommentairePublication> commentaires = commentaireDAO.listerParPublication(publication.getId());
                publication.setCommentaires(commentaires);

                for (CommentairePublication c : commentaires) {
                    String auteurNom = "Utilisateur";
                    // Utilisation de l'EM fourni par le filtre
                    Utilisateur u = em.find(Utilisateur.class, c.getUtilisateurId());
                    if (u != null) {
                        auteurNom = u.getPrenom() + " " + u.getNom();
                    } else {
                        Etudiant e = em.find(Etudiant.class, c.getUtilisateurId());
                        if (e != null) auteurNom = e.getPrenom() + " " + e.getNom();
                    }
                    request.setAttribute("auteur_commentaire_" + c.getId(), auteurNom);
                }

                publication.setNombreLikes(likeDAO.nombreLikes(publication.getId()));
            }

            request.setAttribute("publications", publications);
            request.getRequestDispatcher("/pages/publications.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des publications", e);
        }
        // PAS DE em.close() ici ! Le filtre s'en chargera.
    }
}