package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/etudiant/changerMotDePasse")
public class ChangerMotDePasseEtudiantServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession();

        String motDePasse =
                request.getParameter("motDePasse");

        String confirmation =
                request.getParameter("confirmation");

        // =========================
        // Vérification champs vides
        // =========================

        if (motDePasse == null ||
                confirmation == null ||
                motDePasse.trim().isEmpty() ||
                confirmation.trim().isEmpty()) {

            session.setAttribute(
                    "erreur",
                    "Tous les champs sont obligatoires."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/nouveauMotDePasseEtudiant.jsp"
            );

            return;
        }

        // =========================
        // Vérification longueur
        // =========================

        if (motDePasse.trim().length() < 6) {

            session.setAttribute(
                    "erreur",
                    "Le mot de passe doit contenir au moins 6 caractères."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/nouveauMotDePasseEtudiant.jsp"
            );

            return;
        }

        // =========================
        // Vérification confirmation
        // =========================

        if (!motDePasse.equals(confirmation)) {

            session.setAttribute(
                    "erreur",
                    "Les mots de passe ne correspondent pas."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/nouveauMotDePasseEtudiant.jsp"
            );

            return;
        }

        String email =
                (String) session.getAttribute("resetEmailEtudiant");

        if (email == null) {

            session.setAttribute(
                    "erreur",
                    "Session expirée. Veuillez recommencer."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/motDePasseOublieEtudiant.jsp"
            );

            return;
        }

        EntityManager em = null;

        try {

            em = EntityManagerHelper.getEntityManager();

            EtudiantDAO dao =
                    new EtudiantDAO(em);

            Etudiant etudiant =
                    dao.trouverParEmail(email);

            if (etudiant == null) {

                session.setAttribute(
                        "erreur",
                        "Étudiant introuvable."
                );

                response.sendRedirect(
                        request.getContextPath()
                                + "/pages/auth/motDePasseOublieEtudiant.jsp"
                );

                return;
            }

            // =========================
            // Sauvegarde mot de passe
            // =========================

            EntityManagerHelper.beginTransaction();

            etudiant.setMotDePasse(motDePasse);

            em.merge(etudiant);

            EntityManagerHelper.commit();

            // =========================
            // Nettoyage session
            // =========================

            session.removeAttribute("resetCodeEtudiant");
            session.removeAttribute("resetEmailEtudiant");

            // =========================
            // Succès
            // =========================

            session.setAttribute(
                    "success",
                    "Mot de passe modifié avec succès."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/connexionEtudiant.jsp"
            );

        } catch (Exception e) {

            e.printStackTrace();

            EntityManagerHelper.rollback();

            session.setAttribute(
                    "erreur",
                    "Erreur lors de la modification du mot de passe."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/pages/auth/nouveauMotDePasseEtudiant.jsp"
            );

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}