package com.amical.ard.servlets;

import com.amical.ard.dao.*;
import com.amical.ard.entites.*;
import com.amical.ard.utils.EntityManagerHelper; // Assurez-vous d'utiliser votre helper
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
        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            PublicationDAO pubDAO = new PublicationDAO(em);
            CommentairePublicationDAO comDAO = new CommentairePublicationDAO(em);
            LikePublicationDAO likeDAO = new LikePublicationDAO(em);

            List<Publication> publications = pubDAO.listerToutes();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Publication p : publications) {
                // Info Publication
                request.setAttribute("auteur_publication_" + p.getId(), p.getAuteurPrenom() + " " + p.getAuteurNom());
                request.setAttribute("role_auteur_" + p.getId(), p.getAuteurRole() != null ? p.getAuteurRole() : "MEMBRE");
                request.setAttribute("date_publication_" + p.getId(), (p.getDatePublication() != null) ? p.getDatePublication().format(dtf) : "Date inconnue");

                // Récupération des commentaires
                List<CommentairePublication> coms = comDAO.listerParPublication(p.getId());
                p.setCommentaires(coms);

                for(CommentairePublication c : coms) {
                    // Chercher l'utilisateur ou l'étudiant pour chaque commentaire
                    Etudiant etudiant = em.find(Etudiant.class, c.getUtilisateurId());
                    Utilisateur admin = em.find(Utilisateur.class, c.getUtilisateurId());

                    String nomComplet = "Inconnu";
                    if (etudiant != null) {
                        nomComplet = etudiant.getPrenom() + " " + etudiant.getNom();
                    } else if (admin != null) {
                        nomComplet = admin.getPrenom() + " " + admin.getNom();
                    }
                    request.setAttribute("auteur_commentaire_" + c.getId(), nomComplet);
                }
                p.setNombreLikes(likeDAO.nombreLikes(p.getId()));
            }

            request.setAttribute("publications", publications);
            request.getRequestDispatcher("/pages/publications.jsp").forward(request, response);
        } finally { em.close(); }
    }
}