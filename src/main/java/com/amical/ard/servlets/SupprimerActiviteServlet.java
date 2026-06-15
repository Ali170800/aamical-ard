package com.amical.ard.servlets;

import com.amical.ard.dao.ActiviteDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/supprimerActivite")
public class SupprimerActiviteServlet extends HttpServlet {
    private EntityManagerFactory emf;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("amicalePU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));

        EntityManager em = emf.createEntityManager();
        ActiviteDAO activiteDAO = new ActiviteDAO(em);

        try {
            em.getTransaction().begin();
            activiteDAO.supprimer(id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        response.sendRedirect("listeActivites");
    }

    @Override
    public void destroy() {
        if (emf != null) emf.close();
    }
}