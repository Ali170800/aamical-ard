package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.entites.ActionLog;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/pco-dashboard")
public class PcoDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = null;

        try {

            em = JpaUtil.getEntityManagerFactory()
                    .createEntityManager();

            ActionLogDAO actionLogDAO =
                    new ActionLogDAO(em);

            // 🔥 UNIQUEMENT LES LOGS PCO
            List<ActionLog> dernieresActions =
                    actionLogDAO.getDernieresActionsParRole(
                            "PCO",
                            20
                    );

            request.setAttribute(
                    "dernieresActions",
                    dernieresActions
            );

            request.getRequestDispatcher(
                    "/pages/pco-dashboard.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "erreur",
                    "Erreur lors du chargement du dashboard."
            );

            request.getRequestDispatcher(
                    "/pages/pco-dashboard.jsp"
            ).forward(request, response);

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}