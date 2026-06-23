package com.amical.ard.servlets;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/resultats") // C'est cette annotation qui manque
public class ResultatsAdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, jakarta.servlet.ServletException {

        String id = request.getParameter("id");
        // Logique pour récupérer l'élection avec cet ID
        // request.setAttribute("election", electionTrouvee);
        request.getRequestDispatcher("/pages/resultats.jsp").forward(request, response);
    }
}