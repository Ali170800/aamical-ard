package com.amical.ard.servlets;

import com.amical.ard.dao.BureauDAO;
import com.amical.ard.entites.Bureau;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/bureau/membres")
public class AfficherMembresBureauServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String annee = request.getParameter("annee");

        if (annee == null || annee.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/bureau/selectionner");
            return;
        }

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        BureauDAO bureauDAO = new BureauDAO(em);

        // Récupérer les membres du bureau par année de mandat
        List<Bureau> membres = em.createQuery(
                "SELECT b FROM Bureau b WHERE b.anneeMandat = :annee", Bureau.class)
                .setParameter("annee", annee)
                .getResultList();

        em.close();

        request.setAttribute("annee", annee);
        request.setAttribute("membres", membres);
        request.getRequestDispatcher("/pages/listeMembresBureau.jsp").forward(request, response);
    }
}