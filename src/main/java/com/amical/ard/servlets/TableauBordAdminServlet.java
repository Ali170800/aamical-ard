package com.amical.ard.servlets;

import com.amical.ard.dao.ElectionDAO;
import com.amical.ard.entites.Election;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard-elections")
public class TableauBordAdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // On récupère l'EntityManager géré par le filtre
        EntityManager em = (EntityManager) request.getAttribute("em");

        // Récupérer toutes les élections pour le tableau de bord
        List<Election> listeElections = new ElectionDAO(em).toutesLesElections();

        // On transmet la liste à la JSP
        request.setAttribute("listeElections", listeElections);
        request.getRequestDispatcher("/pages/dashboard-elections.jsp").forward(request, response);

        // Pas de em.close() ici : le filtre s'en chargera automatiquement.
    }
}