package com.amical.ard.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

<<<<<<< HEAD
        response.sendRedirect(request.getContextPath() + "/login.jsp");
=======
        response.sendRedirect(request.getContextPath() + "/accueil.jsp");
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
    }
}