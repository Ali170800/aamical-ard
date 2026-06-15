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
import java.util.stream.Collectors;

@WebServlet("/bureau/selectionner")
public class SelectionnerBureauServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        BureauDAO bureauDAO = new BureauDAO(em);

        // Récupérer la liste des années de mandat distinctes
        List<Bureau> membres = bureauDAO.listerTous();
        List<String> anneesMandat = membres.stream()
                .map(Bureau::getAnneeMandat)
                .distinct()
                .collect(Collectors.toList());

        em.close();

        // Envoyer à la JSP
        request.setAttribute("anneesMandat", anneesMandat);
        request.getRequestDispatcher("/pages/selectionnerBureau.jsp").forward(request, response);
    }
}