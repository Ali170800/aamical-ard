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
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation si nécessaire
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String uri = request.getRequestURI().toLowerCase();
        String contextPath = request.getContextPath();

        // 1. Vérification des URLs publiques
        if (isPublicUrl(uri, contextPath)) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Vérification de la session active
        boolean estConnecte = session != null
                && (session.getAttribute("etudiantConnecte") != null
                || session.getAttribute("etudiant") != null
                || session.getAttribute("utilisateurConnecte") != null);

        if (!estConnecte) {
            // Redirection selon le type de zone
            if (uri.contains("etudiant")) {
                response.sendRedirect(contextPath + "/pages/connexionEtudiant.jsp");
            } else {
                response.sendRedirect(contextPath + "/acceuil.jsp");
            }
            return;
        }

        // 3. Si tout est OK, on continue
        chain.doFilter(request, response);
    }

    private boolean isPublicUrl(String uri, String contextPath) {
        uri = uri.toLowerCase();
        String cp = contextPath.toLowerCase();

        return uri.endsWith("/acceuil.jsp")
                || uri.equals(cp + "/")
                || uri.contains("/login")
                || uri.contains("/connexionetudiant.jsp")
                || uri.contains("/liste-publications")
                // Autorise explicitement l'URL de publication des commentaires
                // pour permettre au Servlet de traiter la requête AJAX
                || uri.contains("/etudiant/commenter-publication")
                // Ressources statiques
                || uri.endsWith(".css")
                || uri.endsWith(".js")
                || uri.endsWith(".png")
                || uri.endsWith(".jpg")
                || uri.endsWith(".jpeg");
    }

    @Override
    public void destroy() {
        // Nettoyage si nécessaire
    }
}