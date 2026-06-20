package com.amical.ard.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthentificationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String uri = request.getRequestURI().toLowerCase();
        String contextPath = request.getContextPath();

        // 1. Autoriser les URLs publiques (fichiers statiques, accueil, login)
        if (isPublicUrl(uri, contextPath)) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Vérification de la connexion
        boolean estConnecte = (session != null && (
                session.getAttribute("utilisateurConnecte") != null ||
                        session.getAttribute("etudiantConnecte") != null
        ));

        if (!estConnecte) {
            // Si pas connecté et essaie d'accéder à une zone protégée, on redirige
            if (uri.contains("/etudiant/")) {
                response.sendRedirect(contextPath + "/pages/connexionEtudiant.jsp");
            } else {
                response.sendRedirect(contextPath + "/acceuil.jsp");
            }
            return;
        }

        // 3. PROTECTION DES ZONES SENSIBLES (ADMIN)
        // Seuls les admins peuvent accéder aux routes /admin/
        if (uri.contains("/admin/")) {
            boolean estAdmin = session.getAttribute("utilisateurConnecte") != null;
            if (!estAdmin) {
                // Si un étudiant tente d'accéder à l'admin, on le renvoie vers son espace
                response.sendRedirect(contextPath + "/espace-etudiant");
                return;
            }
        }

        // 4. Tout est conforme, on laisse passer la requête
        chain.doFilter(request, response);
    }

    private boolean isPublicUrl(String uri, String contextPath) {
        return uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png")
                || uri.endsWith(".jpg") || uri.endsWith(".jpeg") || uri.endsWith(".gif")
                || uri.endsWith("/acceuil.jsp") || uri.endsWith("/accueil.jsp")
                || uri.endsWith("/login.jsp") || uri.endsWith("/connexionetudiant.jsp")
                || uri.endsWith("/inscriptionetudiant.jsp") || uri.contains("/etudiant/connexion")
                || uri.contains("/etudiant/inscription") || uri.endsWith("/")
                || uri.contains("/liste-publications"); // Liste publications accessible à tous
    }
}