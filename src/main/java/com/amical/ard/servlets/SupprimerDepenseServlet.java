package com.amical.ard.servlets;

import com.amical.ard.dao.DepenseActiviteDAO;
import com.amical.ard.entites.DepenseActivite;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/supprimerDepense")
public class SupprimerDepenseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            DepenseActiviteDAO dao = new DepenseActiviteDAO(em);
            DepenseActivite d = dao.trouverParId(id);

            if (d != null) {
                int idActivite = d.getActivite().getId(); // On garde l'id de l'activité

                tx.begin();
                dao.supprimer(d);
                tx.commit();

                // ✅ Redirection vers le servlet qui recharge les données correctement
                response.sendRedirect("voirDepenses?id=" + idActivite);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Dépense introuvable.");
            }

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            em.close();
        }
    }
}