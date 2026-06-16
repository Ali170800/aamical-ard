package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/pcs-dashboard")
public class PCSDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = null;

        try {

            em = EntityManagerHelper.getEntityManager();

            ActionLogDAO actionLogDAO =
                    new ActionLogDAO(em);

            // 🔥 UNIQUEMENT LES LOGS PCS
            request.setAttribute(
                    "dernieresActions",
                    actionLogDAO.getDernieresActionsParRole(
                            "PCS",
                            10
                    )
            );

            request.getRequestDispatcher(
                    "/pages/pcs-dashboard.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(e);

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}