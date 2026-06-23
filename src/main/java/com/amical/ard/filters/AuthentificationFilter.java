package com.amical.ard.filters;

import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
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

        // 1. INITIALISATION SYSTÉMATIQUE DE L'ENTITY MANAGER
        // On le fait AVANT toute vérification pour qu'il soit toujours disponible
        EntityManager em = EntityManagerHelper.getEntityManager();
        request.setAttribute("em", em);

        try {
            HttpSession session = request.getSession(false);
            String uri = request.getRequestURI().toLowerCase();
            String contextPath = request.getContextPath();

            // ======================================
            // URLS PUBLIQUES
            // ======================================
            if (isPublicUrl(uri, contextPath)) {
                chain.doFilter(request, response);
                return;
            }

            // ======================================
            // VÉRIFICATION CONNEXION
            // ======================================
            boolean estConnecte = session != null
                    && (session.getAttribute("etudiantConnecte") != null
                    || session.getAttribute("etudiant") != null
                    || session.getAttribute("utilisateurConnecte") != null);

            if (!estConnecte) {
                if (uri.contains("etudiant")) {
                    response.sendRedirect(contextPath + "/pages/connexionEtudiant.jsp");
                } else {
                    response.sendRedirect(contextPath + "/acceuil.jsp");
                }
                return;
            }

            // ======================================
            // PROTECTION ÉTUDIANT / ADMIN
            // ======================================
            boolean estEtudiant = session.getAttribute("etudiantConnecte") != null
                    || "ETUDIANT".equals(session.getAttribute("userType"));

            if (estEtudiant && (uri.contains("/admin") || uri.contains("/bureau"))) {
                response.sendRedirect(contextPath + "/espace-etudiant");
                return;
            }

            chain.doFilter(request, response);

        } finally {
            // 2. FERMETURE SYSTÉMATIQUE
            // Même en cas d'erreur ou de redirection, on ferme la connexion ici
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private boolean isPublicUrl(String uri, String contextPath) {
        uri = uri.toLowerCase();
        String cp = contextPath.toLowerCase();

        return uri.endsWith("/acceuil.jsp")
                || uri.endsWith("/accueil.jsp")
                || uri.equals(cp + "/")
                || uri.endsWith("/login")
                || uri.endsWith("/login.jsp")
                || uri.contains("/logoutservlet")
                || uri.contains("/etudiant/logout")
                || uri.contains("/etudiant/paydunya/callback")
                || uri.contains("/etudiant/caravanes")
                || uri.contains("?success=true")
                || uri.contains("?cancel=true")
                || uri.contains("/motdepasseoublie.jsp")
                || uri.contains("/verificationcode.jsp")
                || uri.contains("/nouveaumotdepasse.jsp")
                || uri.contains("/verifieremailrecuperation")
                || uri.contains("/verifiercodereset")
                || uri.contains("/changermotdepasse")
                || uri.contains("/motdepasseoublieetudiant.jsp")
                || uri.contains("/verificationcodeetudiant.jsp")
                || uri.contains("/nouveaumotdepasseetudiant.jsp")
                || uri.contains("/etudiant/verifieremailrecuperation")
                || uri.contains("/etudiant/verifiercodereset")
                || uri.contains("/etudiant/changermotdepasse")
                || uri.endsWith("/connexionetudiant.jsp")
                || uri.endsWith("/inscriptionetudiant.jsp")
                || uri.endsWith("/activationcompte.jsp")
                || uri.endsWith("/creationmotdepasse.jsp")
                || uri.contains("/etudiant/activer")
                || uri.contains("/etudiant/valider")
                || uri.contains("/etudiant/creer-motdepasse")
                || uri.contains("/etudiant/connexion")
                || uri.contains("/etudiant/inscription")
                || uri.contains("/admin/verifier")
                || uri.contains("/admin/verifier-code")
                || uri.contains("/admin/activer")
                || uri.contains("/liste-publications")
                || uri.contains("/admin/supprimer-publication")
                || uri.contains("/upload/ajouterpublication.jsp")
                || uri.endsWith(".css")
                || uri.endsWith(".js")
                || uri.endsWith(".png")
                || uri.endsWith(".jpg")
                || uri.endsWith(".jpeg")
                || uri.endsWith(".gif")
                || uri.endsWith(".svg")
                || uri.endsWith(".woff")
                || uri.endsWith(".woff2");
    }
}