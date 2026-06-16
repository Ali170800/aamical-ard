package com.amical.ard.servlets;

import com.amical.ard.dao.FichierJointDAO;
import com.amical.ard.entites.FichierJoint;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@WebServlet("/fichiers/upload")

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 15 * 1024 * 1024
)

public class UploadFichierServlet extends HttpServlet {

    private static final String DOSSIER_UPLOAD = "uploads/pdf";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/pages/fichiers/uploadFichier.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute("utilisateurConnecte");

        if (utilisateur == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );

            return;
        }

        String titre = request.getParameter("titre");
        String type = request.getParameter("typeFichier");

        Part fichierPart = request.getPart("pdf");

        if (fichierPart == null || fichierPart.getSize() == 0) {

            request.setAttribute(
                    "erreur",
                    "Veuillez sélectionner un PDF."
            );

            doGet(request, response);
            return;
        }

        if (!"application/pdf".equalsIgnoreCase(
                fichierPart.getContentType())) {

            request.setAttribute(
                    "erreur",
                    "Seuls les fichiers PDF sont autorisés."
            );

            doGet(request, response);
            return;
        }

        String nomOriginal =
                Paths.get(
                        fichierPart.getSubmittedFileName()
                ).getFileName().toString();

        String extension = ".pdf";

        String nouveauNom =
                UUID.randomUUID() + extension;

        String cheminUpload =
                getServletContext().getRealPath("")
                        + File.separator
                        + DOSSIER_UPLOAD;

        File dossier = new File(cheminUpload);

        if (!dossier.exists()) {
            dossier.mkdirs();
        }

        String cheminComplet =
                cheminUpload
                        + File.separator
                        + nouveauNom;

        fichierPart.write(cheminComplet);

        EntityManager em = null;

        try {

            em = JpaUtil.getEntityManagerFactory()
                    .createEntityManager();

            FichierJointDAO dao =
                    new FichierJointDAO(em);

            FichierJoint fichier =
                    new FichierJoint();

            fichier.setNomFichier(nomOriginal);

            fichier.setTitre(titre);

            fichier.setCheminFichier(cheminComplet);

            fichier.setDateUpload(new Date());

            fichier.setNomAuteur(
                    utilisateur.getPrenom()
                            + " "
                            + utilisateur.getNom()
            );

            fichier.setRoleAuteur(
                    utilisateur.getRole()
            );

            fichier.setTypeFichier(
                    FichierJoint.TypeFichier.valueOf(type)
            );

            dao.ajouter(fichier);

            response.sendRedirect(
                    request.getContextPath()
                            + "/fichiers/liste"
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "erreur",
                    "Erreur upload PDF."
            );

            doGet(request, response);

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}