package com.amical.ard.servlets;

import com.amical.ard.dao.FichierJointDAO;
import com.amical.ard.entites.FichierJoint;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/fichiers/modifier")

@MultipartConfig
public class ModifierFichierServlet extends HttpServlet {

    private static final String DOSSIER_UPLOAD = "uploads/pdf";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int id =
                Integer.parseInt(
                        request.getParameter("id")
                );

        EntityManager em =
                JpaUtil.getEntityManagerFactory()
                        .createEntityManager();

        try {

            FichierJointDAO dao =
                    new FichierJointDAO(em);

            request.setAttribute(
                    "fichier",
                    dao.trouverParId(id)
            );

            request.getRequestDispatcher(
                    "/pages/fichiers/modifierFichier.jsp"
            ).forward(request, response);

        } finally {

            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int id =
                Integer.parseInt(
                        request.getParameter("id")
                );

        EntityManager em =
                JpaUtil.getEntityManagerFactory()
                        .createEntityManager();

        try {

            FichierJointDAO dao =
                    new FichierJointDAO(em);

            FichierJoint fichier =
                    dao.trouverParId(id);

            fichier.setTitre(
                    request.getParameter("titre")
            );

            fichier.setTypeFichier(
                    FichierJoint.TypeFichier.valueOf(
                            request.getParameter("typeFichier")
                    )
            );

            Part nouveauPdf =
                    request.getPart("pdf");

            if (nouveauPdf != null
                    && nouveauPdf.getSize() > 0) {

                File ancien =
                        new File(
                                fichier.getCheminFichier()
                        );

                if (ancien.exists()) {
                    ancien.delete();
                }

                String nomOriginal =
                        Paths.get(
                                nouveauPdf.getSubmittedFileName()
                        ).getFileName().toString();

                String nouveauNom =
                        UUID.randomUUID() + ".pdf";

                String cheminUpload =
                        getServletContext().getRealPath("")
                                + File.separator
                                + DOSSIER_UPLOAD;

                String cheminComplet =
                        cheminUpload
                                + File.separator
                                + nouveauNom;

                nouveauPdf.write(cheminComplet);

                fichier.setNomFichier(nomOriginal);

                fichier.setCheminFichier(cheminComplet);
            }

            dao.modifier(fichier);

            response.sendRedirect(
                    request.getContextPath()
                            + "/fichiers/liste"
            );

        } finally {

            em.close();
        }
    }
}