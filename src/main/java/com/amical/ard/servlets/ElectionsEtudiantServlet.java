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

@WebServlet("/etudiant/elections")
public class ElectionsEtudiantServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // On récupère l'EntityManager préparé par le filtre
        EntityManager em = (EntityManager) request.getAttribute("em");

        try {
            // 1. Récupérer la liste des élections via le DAO
            ElectionDAO electionDAO = new ElectionDAO(em);
            List<Election> listeElections = electionDAO.listerTout();

            // 2. Transmettre la liste à la JSP sous forme d'attribut
            request.setAttribute("listeElections", listeElections);

            // 3. Rediriger vers la page JSP
            request.getRequestDispatcher("/pages/listeElections.jsp").forward(request, response);

        } catch (Exception e) {
            // En cas d'erreur, on délègue au conteneur ou on logue l'erreur
            throw new ServletException("Erreur lors de la récupération des élections", e);
        }
        // Pas de em.close() ici : le filtre s'en occupe dans son bloc finally.
    }
}