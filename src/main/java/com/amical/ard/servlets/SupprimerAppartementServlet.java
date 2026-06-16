package com.amical.ard.servlets;

import com.amical.ard.dao.AppartementDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/supprimerAppartement")
public class SupprimerAppartementServlet extends HttpServlet {

    private AppartementDAO appartementDAO;

    @Override
    public void init() throws ServletException {
        EntityManager em = Persistence.createEntityManagerFactory("amicalePU").createEntityManager();
        appartementDAO = new AppartementDAO(em);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                appartementDAO.supprimer(id);
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Log d'erreur en cas d'ID invalide
            }
        }

        // Redirection vers la liste après suppression
        response.sendRedirect(request.getContextPath() + "/listeAppartements");
    }
}