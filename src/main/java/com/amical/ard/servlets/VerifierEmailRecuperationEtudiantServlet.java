package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EmailUtil;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Random;

@WebServlet("/etudiant/verifierEmailRecuperation")
public class VerifierEmailRecuperationEtudiantServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String email =
                request.getParameter("email");

        EntityManager em = null;

        try {

            em = EntityManagerHelper.getEntityManager();

            EtudiantDAO dao =
                    new EtudiantDAO(em);

            Etudiant etudiant =
                    dao.trouverParEmail(email);

            if (etudiant == null) {

                request.getSession().setAttribute(
                        "erreur",
                        "Email introuvable."
                );

                response.sendRedirect(
                        request.getContextPath()
                                + "/pages/auth/motDePasseOublieEtudiant.jsp"
                );

                return;
            }

            String code =
                    String.valueOf(
                            100000 + new Random().nextInt(900000)
                    );

            HttpSession session =
                    request.getSession();

            session.setAttribute(
                    "resetCodeEtudiant",
                    code
            );

            session.setAttribute(
                    "resetEmailEtudiant",
                    email
            );

            EmailUtil.envoyerEmail(
                    email,
                    "Code récupération",
                    "Votre code est : " + code
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/verificationCodeEtudiant.jsp"
            );

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}