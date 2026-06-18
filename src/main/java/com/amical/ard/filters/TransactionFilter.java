package com.amical.ard.filters;

import com.amical.ard.utils.EntityManagerHelper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*") // Intercepte toutes les requêtes
public class TransactionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // Le filtre laisse passer la requête vers votre servlet
            chain.doFilter(request, response);
        } catch (Exception e) {
            // En cas d'erreur dans le servlet, on rollback
            EntityManagerHelper.rollback();
            throw new ServletException(e);
        } finally {
            // C'EST ICI LA CLÉ : On force la fermeture quoi qu'il arrive
            // Cela libère la connexion et empêche le "max_user_connections"
            EntityManagerHelper.closeEntityManager();
        }
    }
}