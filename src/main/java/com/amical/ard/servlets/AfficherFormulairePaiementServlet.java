package com.amical.ard.servlets;

import com.amical.ard.dao.LogementEtudiantDAO;
import com.amical.ard.entites.LogementEtudiant;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/afficherFormulairePaiement")
public class AfficherFormulairePaiementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = EntityManagerHelper.getEntityManager();

        try {
            LogementEtudiantDAO logementDAO = new LogementEtudiantDAO(em);
            List<LogementEtudiant> logements = logementDAO.listerTous();

            request.setAttribute("logements", logements);

            request.getRequestDispatcher("/pages/ajouterPaiement.jsp").forward(request, response);

        } finally {
            em.close();
        }
    }
}