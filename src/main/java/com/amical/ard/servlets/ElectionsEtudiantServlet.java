package com.amical.ard.servlets;

import com.amical.ard.dao.ElectionDAO;
import com.amical.ard.entites.Election;
import com.amical.ard.utils.EntityManagerHelper;
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

        EntityManager em = EntityManagerHelper.getEntityManager();
        try {
            // 1. Récupérer la liste des élections via le DAO
            ElectionDAO electionDAO = new ElectionDAO(em);
            List<Election> listeElections = electionDAO.listerTout();

            // 2. Transmettre la liste à la JSP sous forme d'attribut
            request.setAttribute("listeElections", listeElections);

            // 3. Rediriger vers la page JSP
            request.getRequestDispatcher("/pages/listeElections.jsp").forward(request, response);

        } catch (Exception e) {
            // En cas d'erreur, on affiche une erreur 500 ou on redirige
            throw new ServletException("Erreur lors de la récupération des élections", e);
        } finally {
            em.close();
        }
    }
}