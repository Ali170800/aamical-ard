package com.amical.ard.servlets;

import com.amical.ard.dao.ElectionDAO;
import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/supprimer-election")
public class SupprimerElectionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");

        if (idStr != null) {
            // Récupération de l'EntityManager injecté par le filtre
            EntityManager em = (EntityManager) request.getAttribute("em");

            try {
                em.getTransaction().begin();

                // Appel au DAO pour supprimer l'élection
                ElectionDAO dao = new ElectionDAO(em);
                dao.supprimer(Long.parseLong(idStr));

                em.getTransaction().commit();
                response.sendRedirect(request.getContextPath() + "/admin/dashboard-elections?status=deleted");

            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/admin/dashboard-elections?status=error");
            }
            // Pas de em.close() ici : le filtre s'en occupe automatiquement.
        }
    }
}