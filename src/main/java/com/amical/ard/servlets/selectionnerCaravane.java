package com.amical.ard.servlets;

import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/participants/selectionner")
public class selectionnerCaravane extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        CaravaneDAO caravaneDAO = new CaravaneDAO(em);

        List<Caravane> caravanes = caravaneDAO.listerTous();
        request.setAttribute("caravanes", caravanes);

        em.close();

        request.getRequestDispatcher("/pages/selectionnerCaravane.jsp").forward(request, response);
    }
}