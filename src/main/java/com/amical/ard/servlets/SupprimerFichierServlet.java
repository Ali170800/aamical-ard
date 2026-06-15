package com.amical.ard.servlets;

import com.amical.ard.dao.FichierJointDAO;
import com.amical.ard.entites.FichierJoint;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;

@WebServlet("/fichiers/supprimer")
public class SupprimerFichierServlet extends HttpServlet {

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

            FichierJoint fichier =
                    dao.trouverParId(id);

            if (fichier != null) {

                File pdf =
                        new File(
                                fichier.getCheminFichier()
                        );

                if (pdf.exists()) {
                    pdf.delete();
                }

                dao.supprimer(fichier);
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/fichiers/liste"
            );

        } finally {

            em.close();
        }
    }
}