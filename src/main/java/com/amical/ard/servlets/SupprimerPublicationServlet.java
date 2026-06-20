package com.amical.ard.servlets;

import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.entites.Publication;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.EntityManagerHelper; // Import essentiel

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;

@WebServlet("/admin/supprimer-publication")
public class SupprimerPublicationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // On récupère l'EntityManager via l'Helper.
        // C'est le filtre qui s'assurera de fermer cette connexion automatiquement.
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/liste-publications");
                return;
            }

            Long publicationId = Long.parseLong(idParam);
            HttpSession session = request.getSession(false);
            Utilisateur admin = (session != null) ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;

            if (admin == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            PublicationDAO dao = new PublicationDAO(em);
            Publication publication = dao.trouver(publicationId);

            if (publication == null || !publication.getAuteurId().equals(admin.getId().longValue())) {
                response.sendRedirect(request.getContextPath() + "/liste-publications");
                return;
            }

            // Suppression physique du fichier
            if (publication.getImage() != null) {
                String cheminImage = getServletContext().getRealPath("/uploads") + File.separator + publication.getImage();
                File fichier = new File(cheminImage);
                if (fichier.exists()) {
                    fichier.delete();
                }
            }

            // Suppression en BDD
            dao.supprimer(publicationId);

            response.sendRedirect(request.getContextPath() + "/liste-publications");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Erreur suppression : " + e.getMessage());
        }
        // PAS DE finally { em.close(); } ICI. C'est le filtre qui le gère.
    }
}