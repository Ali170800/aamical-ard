
package com.amical.ard.servlets;

import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.entites.Publication;
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

import java.io.File;
import java.io.IOException;

@WebServlet("/admin/supprimer-publication")
public class SupprimerPublicationServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() {

        emf =
                Persistence.createEntityManagerFactory(
                        "amicalePU"
                );
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em =
                emf.createEntityManager();

        try {

            // =========================
            // PARAMÈTRE
            // =========================

            String idParam =
                    request.getParameter("id");

            if(idParam == null
                    || idParam.isEmpty()){

                response.sendRedirect(
                        request.getContextPath()
                                + "/liste-publications"
                );

                return;
            }

            Long publicationId =
                    Long.parseLong(idParam);

            // =========================
            // SESSION
            // =========================

            HttpSession session =
                    request.getSession();

            Utilisateur admin =
                    (Utilisateur)
                            session.getAttribute(
                                    "utilisateurConnecte"
                            );

            if(admin == null){

                response.sendRedirect(
                        request.getContextPath()
                                + "/login.jsp"
                );

                return;
            }

            Long adminId =
                    admin.getId().longValue();

            // =========================
            // DAO
            // =========================

            PublicationDAO dao =
                    new PublicationDAO(em);

            Publication publication =
                    dao.trouver(publicationId);

            // =========================
            // SÉCURITÉ
            // =========================

            if(publication == null
                    ||
                    publication.getAuteurId() == null
                    ||
                    !publication.getAuteurId()
                            .equals(adminId)){

                response.sendRedirect(
                        request.getContextPath()
                                + "/liste-publications"
                );

                return;
            }

            // =========================
            // SUPPRESSION IMAGE
            // =========================

            if(publication.getImage() != null){

                String cheminImage =
                        getServletContext()
                                .getRealPath("/uploads")
                                + File.separator
                                + publication.getImage();

                File fichier =
                        new File(cheminImage);

                if(fichier.exists()){

                    fichier.delete();
                }
            }

            // =========================
            // SUPPRESSION BDD
            // =========================

            dao.supprimer(publicationId);

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
                    "Erreur suppression : "
                            + e.getMessage()
            );

        } finally {

            em.close();
        }
    }

    @Override
    public void destroy() {

        if(emf != null){

            emf.close();
        }
    }
}

