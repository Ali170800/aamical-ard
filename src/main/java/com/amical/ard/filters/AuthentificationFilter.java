package com.amical.ard.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
<<<<<<< HEAD
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/pages/*")
public class AuthentificationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        boolean connecte = session != null && session.getAttribute("utilisateurConnecte") != null;

        if (connecte) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login.jsp");
        }
=======
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthentificationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request =
                (HttpServletRequest) req;

        HttpServletResponse response =
                (HttpServletResponse) res;

        HttpSession session =
                request.getSession(false);

        String uri =
                request.getRequestURI().toLowerCase();

        String contextPath =
                request.getContextPath();

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

        boolean estConnecte =
                session != null
                        &&
                        (
                                session.getAttribute("etudiantConnecte") != null
                                        ||
                                        session.getAttribute("etudiant") != null
                                        ||
                                        session.getAttribute("utilisateurConnecte") != null
                        );

        // ======================================
        // UTILISATEUR NON CONNECTÉ
        // ======================================

        if (!estConnecte) {

            if (uri.contains("etudiant")) {

                response.sendRedirect(
                        contextPath +
                                "/pages/connexionEtudiant.jsp"
                );

            } else {

                response.sendRedirect(
                        contextPath +
                                "/acceuil.jsp"
                );
            }

            return;
        }

        // ======================================
        // PROTECTION ÉTUDIANT / ADMIN
        // ======================================

        boolean estEtudiant =
                session.getAttribute("etudiantConnecte") != null
                        ||
                        "ETUDIANT".equals(
                                session.getAttribute("userType")
                        );

        if (
                estEtudiant
                        &&
                        (
                                uri.contains("/admin")
                                        ||
                                        uri.contains("/bureau")
                        )
        ) {

            response.sendRedirect(
                    contextPath +
                            "/espace-etudiant"
            );

            return;
        }

        chain.doFilter(request, response);
    }

    // =====================================================
    // MÉTHODE URL PUBLIQUE
    // =====================================================

    private boolean isPublicUrl(String uri,
                                String contextPath) {

        uri = uri.toLowerCase();

        return

                // =====================================
                // ACCUEIL
                // =====================================

                uri.endsWith("/acceuil.jsp")
                        ||
                        uri.endsWith("/accueil.jsp")
                        ||
                        uri.equals(contextPath.toLowerCase() + "/")

                        // =====================================
                        // LOGIN
                        // =====================================

                        ||
                        uri.endsWith("/login")
                        ||
                        uri.endsWith("/login.jsp")

                        // =====================================
                        // LOGOUT
                        // =====================================

                        ||
                        uri.contains("/logoutservlet")
                        ||
                        uri.contains("/etudiant/logout")

                        // =====================================
                        // PAYDUNYA CALLBACK
                        // =====================================

                        ||
                        uri.contains("/etudiant/paydunya/callback")

                        ||
                        uri.contains("/etudiant/caravanes")

                        ||
                        uri.contains("?success=true")

                        ||
                        uri.contains("?cancel=true")

                        // =====================================
                        // MOT DE PASSE OUBLIÉ ADMIN
                        // =====================================

                        ||
                        uri.contains("/motdepasseoublie.jsp")

                        ||
                        uri.contains("/verificationcode.jsp")

                        ||
                        uri.contains("/nouveaumotdepasse.jsp")

                        ||
                        uri.contains("/verifieremailrecuperation")

                        ||
                        uri.contains("/verifiercodereset")

                        ||
                        uri.contains("/changermotdepasse")

                        // =====================================
                        // MOT DE PASSE OUBLIÉ ÉTUDIANT
                        // =====================================

                        ||
                        uri.contains("/motdepasseoublieetudiant.jsp")

                        ||
                        uri.contains("/verificationcodeetudiant.jsp")

                        ||
                        uri.contains("/nouveaumotdepasseetudiant.jsp")

                        ||
                        uri.contains("/etudiant/verifieremailrecuperation")

                        ||
                        uri.contains("/etudiant/verifiercodereset")

                        ||
                        uri.contains("/etudiant/changermotdepasse")

                        // =====================================
                        // ÉTUDIANT
                        // =====================================

                        ||
                        uri.endsWith("/connexionetudiant.jsp")

                        ||
                        uri.endsWith("/inscriptionetudiant.jsp")

                        ||
                        uri.endsWith("/activationcompte.jsp")

                        ||
                        uri.endsWith("/creationmotdepasse.jsp")

                        ||
                        uri.contains("/etudiant/activer")

                        ||
                        uri.contains("/etudiant/valider")

                        ||
                        uri.contains("/etudiant/creer-motdepasse")

                        ||
                        uri.contains("/etudiant/connexion")

                        ||
                        uri.contains("/etudiant/inscription")

                        // =====================================
                        // ADMIN
                        // =====================================

                        ||
                        uri.contains("/admin/verifier")

                        ||
                        uri.contains("/admin/verifier-code")

                        ||
                        uri.contains("/admin/activer")

                        // =====================================
                        // PUBLICATIONS
                        // =====================================

                        ||
                        uri.contains("/liste-publications")

                        ||
                        uri.contains("/admin/supprimer-publication")

                        ||
                        uri.contains("/upload/ajouterpublication.jsp")

                        // =====================================
                        // FICHIERS STATIQUES
                        // =====================================

                        ||
                        uri.endsWith(".css")

                        ||
                        uri.endsWith(".js")

                        ||
                        uri.endsWith(".png")

                        ||
                        uri.endsWith(".jpg")

                        ||
                        uri.endsWith(".jpeg")

                        ||
                        uri.endsWith(".gif")

                        ||
                        uri.endsWith(".svg")

                        ||
                        uri.endsWith(".woff")

                        ||
                        uri.endsWith(".woff2");
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
    }
}