package com.amical.ard.servlets;

import com.amical.ard.dao.BureauDAO;
import com.amical.ard.entites.Bureau;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/bureau/supprimer")
public class SupprimerBureauServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        BureauDAO bureauDAO = new BureauDAO(em);

        Bureau bureau = bureauDAO.trouverParId(id);
        if (bureau != null) {
            bureauDAO.supprimer(bureau);
        }

        em.close();
        response.sendRedirect(request.getContextPath() + "/bureau/selectionner");
    }
}