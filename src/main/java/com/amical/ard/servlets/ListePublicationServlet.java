package com.amical.ard.servlets;

import com.amical.ard.dao.*;
import com.amical.ard.entites.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/liste-publications")
public class ListePublicationServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() { emf = Persistence.createEntityManagerFactory("amicalePU"); }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();
        try {
            PublicationDAO pubDAO = new PublicationDAO(em);
            CommentairePublicationDAO comDAO = new CommentairePublicationDAO(em);
            LikePublicationDAO likeDAO = new LikePublicationDAO(em);

            List<Publication> publications = pubDAO.listerToutes();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Publication p : publications) {
                // Nom + Rôle
                request.setAttribute("auteur_publication_" + p.getId(), p.getAuteurPrenom() + " " + p.getAuteurNom());
                request.setAttribute("role_auteur_" + p.getId(), p.getAuteurRole() != null ? p.getAuteurRole() : "MEMBRE");

                // Date formatée
                String datePub = (p.getDatePublication() != null) ? p.getDatePublication().format(dtf) : "Date inconnue";
                request.setAttribute("date_publication_" + p.getId(), datePub);

                // Commentaires
                List<CommentairePublication> coms = comDAO.listerParPublication(p.getId());
                p.setCommentaires(coms);
                for(CommentairePublication c : coms) {
                    request.setAttribute("auteur_commentaire_" + c.getId(), "Utilisateur"); // Simplifié pour l'exemple
                }

                p.setNombreLikes(likeDAO.nombreLikes(p.getId()));
            }

            request.setAttribute("publications", publications);
            request.getRequestDispatcher("/pages/publications.jsp").forward(request, response);
        } finally { em.close(); }
    }
}