package com.amical.ard.servlets;

import com.amical.ard.entites.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/redirect-to-dashboard")
public class RedirectToDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔐 1. Récupération session (sans en créer une nouvelle)
        HttpSession session = request.getSession(false);

        if (session == null) {
            System.out.println("❌ Session inexistante → login");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 🔐 2. Récupération utilisateur connecté
        Utilisateur user = (Utilisateur) session.getAttribute("utilisateurConnecte");

        if (user == null) {
            System.out.println("❌ Aucun utilisateur en session → login");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            System.out.println("❌ Rôle invalide → login");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 🔥 3. Normalisation du rôle
        String role = user.getRole().trim().toUpperCase();

        System.out.println("✅ Utilisateur connecté : " + user.getPrenom() + " " + user.getNom());
        System.out.println("✅ ROLE DETECTÉ = [" + role + "]");

        // 🚀 4. Redirection selon rôle
        switch (role) {

            // ✅ Commission Sociale
            case "PCS":
                response.sendRedirect(request.getContextPath() + "/pages/pcs-dashboard.jsp");
                break;

            // ✅ Commission Organisation
            case "PCO":
                response.sendRedirect(request.getContextPath() + "/pages/pco-dashboard.jsp");
                break;

            // ✅ Admin / Super Admin
            case "ADMIN":
            case "SUPER_ADMIN":
                response.sendRedirect(request.getContextPath() + "/pages/cahier.jsp");
                break;

            // ❌ Cas inattendu
            default:
                System.out.println("⚠️ Rôle inconnu : " + role);
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                break;
        }
    }

    // 🔁 Support POST aussi (sécurité + compatibilité)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}