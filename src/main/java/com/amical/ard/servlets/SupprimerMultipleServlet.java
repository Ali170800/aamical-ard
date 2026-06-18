package com.amical.ard.servlets;

import com.amical.ard.dao.PaiementLogementDAO;
import com.amical.ard.entites.PaiementLogement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/supprimerMultiplePaiements")
public class SupprimerMultipleServlet extends HttpServlet {

    private static EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("amicalePU");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération des IDs sélectionnés dans la JSP
        String[] ids = request.getParameterValues("ids");

        if (ids != null && ids.length > 0) {
            EntityManager em = emf.createEntityManager();
            try {
                PaiementLogementDAO dao = new PaiementLogementDAO(em);

                // On boucle sur chaque ID pour supprimer
                for (String idStr : ids) {
                    try {
                        int id = Integer.parseInt(idStr);
                        PaiementLogement p = dao.trouverParId(id);
                        if (p != null) {
                            dao.supprimer(p);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }

                request.getSession().setAttribute("success", "Suppression des paiements effectuée avec succès.");

            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Erreur critique lors de la suppression.");
            } finally {
                em.close();
            }
        } else {
            request.getSession().setAttribute("error", "Veuillez sélectionner au moins un paiement à supprimer.");
        }

        // Redirection vers la liste des paiements
        response.sendRedirect("listePaiements");
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}