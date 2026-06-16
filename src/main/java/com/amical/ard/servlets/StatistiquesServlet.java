package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/statistiques")
public class StatistiquesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EtudiantDAO etudiantDAO = new EtudiantDAO(em);

        // Totaux
        long totalEtudiants = etudiantDAO.compterTous();

        // Sexe (cohérent avec "Masculin"/"Féminin")
        long totalGarcons = etudiantDAO.compterParSexe("Masculin");
        long totalFilles  = etudiantDAO.compterParSexe("Féminin");

        // Logement
        long totalLoges     = etudiantDAO.compterEtudiantsLoges();
        long totalNonLoges  = Math.max(0, totalEtudiants - totalLoges);

        // Par filière
        Map<String, Long> statsParFiliere = etudiantDAO.compterParFiliere();

        em.close();

        // Vers JSP
        request.setAttribute("totalEtudiants", totalEtudiants);
        request.setAttribute("totalGarcons", totalGarcons);
        request.setAttribute("totalFilles", totalFilles);
        request.setAttribute("totalLoges", totalLoges);
        request.setAttribute("totalNonLoges", totalNonLoges);
        request.setAttribute("statsParFiliere", statsParFiliere);

        request.getRequestDispatcher("/pages/statistiques.jsp").forward(request, response);
    }
}