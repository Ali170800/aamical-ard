package com.amical.ard.servlets;

import com.amical.ard.dao.DepenseActiviteDAO;
import com.amical.ard.dao.ActiviteDAO;
import com.amical.ard.entites.DepenseActivite;
import com.amical.ard.entites.Activite;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.math.BigDecimal;

@WebServlet("/voirDepenses")
public class VoirDepensesServlet extends HttpServlet {

    private EntityManager em;
    private DepenseActiviteDAO depenseDAO;
    private ActiviteDAO activiteDAO;

    @Override
    public void init() {
        em = JpaUtil.getEntityManagerFactory().createEntityManager();
        depenseDAO = new DepenseActiviteDAO(em);
        activiteDAO = new ActiviteDAO(em);
    }

    @Override
    public void destroy() {
        if (em != null && em.isOpen()) em.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idActiviteStr = request.getParameter("id");
        if (idActiviteStr == null || idActiviteStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID activité manquant");
            return;
        }

        int idActivite = Integer.parseInt(idActiviteStr);
        Activite activite = activiteDAO.trouverParId(idActivite);
        List<DepenseActivite> depenses = depenseDAO.listerParActivite(idActivite);

        // Calculer le total
        BigDecimal total = depenses.stream()
                .map(DepenseActivite::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        request.setAttribute("activite", activite);
        request.setAttribute("depenses", depenses);
        request.setAttribute("total", total);

        request.getRequestDispatcher("/pages/voirDepenses.jsp").forward(request, response);
    }
}