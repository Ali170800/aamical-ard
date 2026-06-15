package com.amical.ard.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/etudiant/verifierCodeReset")
public class VerifierCodeResetEtudiantServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String code =
                request.getParameter("code");

        HttpSession session =
                request.getSession();

        String vraiCode =
                (String) session.getAttribute("resetCodeEtudiant");

        if (vraiCode == null
                || !vraiCode.equals(code)) {

            session.setAttribute(
                    "erreur",
                    "Code incorrect."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/verificationCodeEtudiant.jsp"
            );

            return;
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/pages/auth/nouveauMotDePasseEtudiant.jsp"
        );
    }
}