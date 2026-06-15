package com.amical.ard.servlets;

import com.amical.ard.dao.DepenseActiviteDAO;
import com.amical.ard.entites.DepenseActivite;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/listeDepenses")
public class ListeDepensesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            DepenseActiviteDAO depenseDAO = new DepenseActiviteDAO(em);
            List<DepenseActivite> depenses = depenseDAO.listerTous();

            request.setAttribute("listeDepenses", depenses);
            request.getRequestDispatcher("/listeDepenses.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            em.close();
        }
    }
}