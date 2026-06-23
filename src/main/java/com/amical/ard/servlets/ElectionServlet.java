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

@WebServlet("/pages/liste-elections")
public class ElectionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération de l'EntityManager injecté par le Filtre
        EntityManager em = (EntityManager) request.getAttribute("em");

        try {
            // Récupération de toutes les élections depuis la base de données
            List<Election> elections = new ElectionDAO(em).listerTout();

            // On passe cette liste à la JSP sous le nom "listeElections"
            request.setAttribute("listeElections", elections);

            // Redirection vers ta JSP
            request.getRequestDispatcher("/pages/liste-elections.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du chargement des élections.");
        }
        // Pas besoin de em.close() ici, le filtre s'en occupe dans son bloc finally.
    }
}