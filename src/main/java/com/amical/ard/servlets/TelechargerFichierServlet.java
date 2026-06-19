package com.amical.ard.servlets;

import com.amical.ard.dao.FichierJointDAO;
import com.amical.ard.entites.FichierJoint;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/fichiers/ouvrir")
public class TelechargerFichierServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        // Utilisation du Helper global
        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            FichierJointDAO dao = new FichierJointDAO(em);
            FichierJoint fichier = dao.trouverParId(id);

            if (fichier == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            File pdf = new File(fichier.getCheminFichier());

            response.setContentType("application/pdf");
            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=\"" + fichier.getNomFichier() + "\""
            );

            try (FileInputStream in = new FileInputStream(pdf);
                 OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du téléchargement.");
        }
        // finally { em.close(); } supprimé : géré par le Filtre
    }
}