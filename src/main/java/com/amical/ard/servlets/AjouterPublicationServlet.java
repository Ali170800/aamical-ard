
        package com.amical.ard.servlets;

import com.amical.ard.dao.PublicationDAO;
import com.amical.ard.entites.Publication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@WebServlet("/admin/ajouter-publication")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,   // 10 MB
        maxFileSize = 1024L * 1024L * 1024L,    // 1 GB
        maxRequestSize = 1024L * 1024L * 2048L  // 2 GB
)
public class AjouterPublicationServlet extends HttpServlet {

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
            // SESSION
            // =========================

            HttpSession session =
                    request.getSession();

            Object admin =
                    session.getAttribute(
                            "utilisateurConnecte"
                    );

            // =========================
            // SÉCURITÉ
            // =========================

            if (admin == null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/login.jsp"
                );

                return;
            }

            // =========================
            // INFOS AUTEUR
            // =========================

            Integer auteurId = null;

            String nom = "";

            String prenom = "";

            String role = "";

            try {

                auteurId =
                        (Integer)
                                admin.getClass()
                                        .getMethod("getId")
                                        .invoke(admin);

                nom =
                        (String)
                                admin.getClass()
                                        .getMethod("getNom")
                                        .invoke(admin);

                prenom =
                        (String)
                                admin.getClass()
                                        .getMethod("getPrenom")
                                        .invoke(admin);

                role =
                        (String)
                                admin.getClass()
                                        .getMethod("getRole")
                                        .invoke(admin);

            } catch (Exception e) {

                e.printStackTrace();
            }

            // =========================
            // DESCRIPTION
            // =========================

            String description =
                    request.getParameter(
                            "description"
                    );

            // =========================
            // IMAGE
            // =========================

            Part imagePart =
                    request.getPart("image");

            String nomFichier =
                    Paths.get(
                                    imagePart.getSubmittedFileName()
                            )
                            .getFileName()
                            .toString();

            // =========================
            // DOSSIER UPLOAD
            // =========================

            String uploadPath =
                    getServletContext()
                            .getRealPath("/uploads");

            File uploadDir =
                    new File(uploadPath);

            if (!uploadDir.exists()) {

                uploadDir.mkdirs();
            }

            // =========================
            // SAUVEGARDE IMAGE
            // =========================

            imagePart.write(
                    uploadPath
                            + File.separator
                            + nomFichier
            );

            // =========================
            // PUBLICATION
            // =========================

            Publication publication =
                    new Publication();

            publication.setDescription(
                    description
            );

            publication.setImage(
                    nomFichier
            );

            publication.setAuteurId(
                    auteurId.longValue()
            );

            publication.setAuteurType(
                    "ADMIN"
            );

            publication.setAuteurNom(
                    nom
            );

            publication.setAuteurPrenom(
                    prenom
            );

            publication.setAuteurRole(
                    role
            );

            publication.setDatePublication(
                    LocalDateTime.now()
            );

            // =========================
            // SAUVEGARDE
            // =========================

            PublicationDAO dao =
                    new PublicationDAO(em);

            dao.ajouter(publication);

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
                    "Erreur : "
                            + e.getMessage()
            );

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
