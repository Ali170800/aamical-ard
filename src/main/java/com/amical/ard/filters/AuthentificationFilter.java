package com.amical.ard.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
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
    }
}