package com.amical.ard.servlets;

import com.amical.ard.dao.BureauDAO;
import com.amical.ard.entites.Bureau;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/bureau/modifier")
public class ModifierBureauServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        BureauDAO bureauDAO = new BureauDAO(em);

        Bureau bureau = bureauDAO.trouverParId(id);
        em.close();

        if (bureau == null) {
            response.sendRedirect(request.getContextPath() + "/bureau/selectionner");
            return;
        }

        request.setAttribute("bureau", bureau);
        request.getRequestDispatcher("/pages/modifierBureau.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String role = request.getParameter("roleBureau");
        String annee = request.getParameter("anneeMandat");
        String numero = request.getParameter("numero");
        String email = request.getParameter("email");

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        BureauDAO bureauDAO = new BureauDAO(em);

        Bureau bureau = bureauDAO.trouverParId(id);
        if (bureau != null) {
            bureau.setRoleBureau(role);
            bureau.setAnneeMandat(annee);
            bureau.setNumero(numero);
            bureau.setEmail(email);
            bureauDAO.modifier(bureau);
        }

        em.close();
        response.sendRedirect(request.getContextPath() + "/bureau/selectionner");
    }
}