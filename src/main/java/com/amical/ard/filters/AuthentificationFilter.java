package com.amical.ard.filters;

import com.amical.ard.utils.EntityManagerHelper;
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

        try {
            // 1. Début de la transaction JPA
            EntityManagerHelper.beginTransaction();

            HttpSession session = request.getSession(false);
            String uri = request.getRequestURI().toLowerCase();
            String contextPath = request.getContextPath();

            // 2. Vérification des URLs Publiques
            if (isPublicUrl(uri, contextPath)) {
                chain.doFilter(request, response);
            } else {
                // 3. Vérification Connexion
                boolean estConnecte = session != null && (session.getAttribute("etudiantConnecte") != null
                        || session.getAttribute("etudiant") != null || session.getAttribute("utilisateurConnecte") != null);

                if (!estConnecte) {
                    if (uri.contains("etudiant")) response.sendRedirect(contextPath + "/pages/connexionEtudiant.jsp");
                    else response.sendRedirect(contextPath + "/acceuil.jsp");
                } else {
                    // Protection Admin/Bureau
                    boolean estEtudiant = session.getAttribute("etudiantConnecte") != null || "ETUDIANT".equals(session.getAttribute("userType"));
                    if (estEtudiant && (uri.contains("/admin") || uri.contains("/bureau"))) {
                        response.sendRedirect(contextPath + "/espace-etudiant");
                    } else {
                        chain.doFilter(request, response);
                    }
                }
            }

            // 4. Commit JPA si aucune exception
            EntityManagerHelper.commit();

        } catch (Exception e) {
            EntityManagerHelper.rollback();
            throw new ServletException(e);
        } finally {
            // 5. Fermeture garantie de la connexion
            EntityManagerHelper.closeEntityManager();
        }
    }

    private boolean isPublicUrl(String uri, String contextPath) {
        uri = uri.toLowerCase();
        String cp = contextPath.toLowerCase();

        return uri.endsWith("/acceuil.jsp") || uri.endsWith("/accueil.jsp") || uri.equals(cp + "/")
                || uri.endsWith("/login") || uri.endsWith("/login.jsp") || uri.contains("/logoutservlet")
                || uri.contains("/etudiant/logout") || uri.contains("/etudiant/paydunya/callback")
                || uri.contains("/etudiant/caravanes") || uri.contains("/etudiant/commenter-publication")
                || uri.contains("?success=true") || uri.contains("?cancel=true")
                || uri.contains("/motdepasseoublie.jsp") || uri.contains("/verificationcode.jsp")
                || uri.contains("/nouveaumotdepasse.jsp") || uri.contains("/verifieremailrecuperation")
                || uri.contains("/verifiercodereset") || uri.contains("/changermotdepasse")
                || uri.contains("/motdepasseoublieetudiant.jsp") || uri.contains("/verificationcodeetudiant.jsp")
                || uri.contains("/nouveaumotdepasseetudiant.jsp") || uri.contains("/etudiant/verifieremailrecuperation")
                || uri.contains("/etudiant/verifiercodereset") || uri.contains("/etudiant/changermotdepasse")
                || uri.endsWith("/connexionetudiant.jsp") || uri.endsWith("/inscriptionetudiant.jsp")
                || uri.endsWith("/activationcompte.jsp") || uri.endsWith("/creationmotdepasse.jsp")
                || uri.contains("/etudiant/activer") || uri.contains("/etudiant/valider")
                || uri.contains("/etudiant/creer-motdepasse") || uri.contains("/etudiant/connexion")
                || uri.contains("/etudiant/inscription") || uri.contains("/admin/verifier")
                || uri.contains("/admin/verifier-code") || uri.contains("/admin/activer")
                || uri.contains("/liste-publications") || uri.contains("/admin/supprimer-publication")
                || uri.contains("/upload/ajouterpublication.jsp") || uri.endsWith(".css") || uri.endsWith(".js")
                || uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".jpeg")
                || uri.endsWith(".gif") || uri.endsWith(".svg") || uri.endsWith(".woff") || uri.endsWith(".woff2");
    }

    @Override
    public void destroy() {
        EntityManagerHelper.closeEntityManagerFactory();
    }
}