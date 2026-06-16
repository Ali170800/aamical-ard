package com.amical.ard.servlets;

import com.amical.ard.dao.ActionLogDAO;
import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.Utilisateur;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/caravane/modifier")
public class ModifierCaravaneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = null;

        try {

            int id =
                    Integer.parseInt(request.getParameter("id"));

            em = JpaUtil.getEntityManagerFactory()
                    .createEntityManager();

            CaravaneDAO caravaneDAO =
                    new CaravaneDAO(em);

            Caravane caravane =
                    caravaneDAO.trouverParId(id);

            if (caravane != null) {

                request.setAttribute(
                        "caravane",
                        caravane
                );

                request.getRequestDispatcher(
                        "/pages/modifierCaravane.jsp"
                ).forward(request, response);

            } else {

                response.sendRedirect(
                        request.getContextPath()
                                + "/caravane/lister"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                            + "/caravane/lister"
            );

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = null;

        try {

            int id =
                    Integer.parseInt(
                            request.getParameter("id")
                    );

            String nom =
                    request.getParameter("nom");

            double montant =
                    Double.parseDouble(
                            request.getParameter("montant")
                    );

            LocalDate date =
                    LocalDate.parse(
                            request.getParameter("date")
                    );

            em = JpaUtil.getEntityManagerFactory()
                    .createEntityManager();

            CaravaneDAO caravaneDAO =
                    new CaravaneDAO(em);

            ActionLogDAO actionLogDAO =
                    new ActionLogDAO(em);

            // ==========================
            // UTILISATEUR CONNECTÉ
            // ==========================

            HttpSession session =
                    request.getSession();

            Utilisateur utilisateurConnecte =
                    (Utilisateur) session.getAttribute(
                            "utilisateurConnecte"
                    );

            em.getTransaction().begin();

            Caravane caravane =
                    caravaneDAO.trouverParId(id);

            if (caravane != null) {

                // ==========================
                // ANCIENNES VALEURS
                // ==========================

                String ancienNom =
                        caravane.getNom();

                double ancienMontant =
                        caravane.getMontant();

                LocalDate ancienneDate =
                        caravane.getDate();

                // ==========================
                // MODIFICATION
                // ==========================

                caravane.setNom(nom);
                caravane.setMontant(montant);
                caravane.setDate(date);

                // ==========================
                // LOG ACTION
                // ==========================

                if (utilisateurConnecte != null) {

                    String details =
                            "Caravane ID=" + id
                                    + " | Nom : "
                                    + ancienNom
                                    + " → "
                                    + nom
                                    + " | Montant : "
                                    + ancienMontant
                                    + " → "
                                    + montant
                                    + " | Date : "
                                    + ancienneDate
                                    + " → "
                                    + date;

                    actionLogDAO.enregistrerAction(
                            utilisateurConnecte.getId(),
                            utilisateurConnecte.getPrenom()
                                    + " "
                                    + utilisateurConnecte.getNom(),
                            utilisateurConnecte.getRole(),
                            "Modification caravane",
                            details
                    );
                }
            }

            em.getTransaction().commit();

            request.getSession().setAttribute(
                    "success",
                    "Caravane modifiée avec succès."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/caravane/lister"
            );

        } catch (Exception e) {

            e.printStackTrace();

            if (em != null
                    && em.getTransaction().isActive()) {

                em.getTransaction().rollback();
            }

            request.setAttribute(
                    "erreur",
                    "Erreur lors de la modification de la caravane."
            );

            request.getRequestDispatcher(
                    "/pages/modifierCaravane.jsp"
            ).forward(request, response);

        } finally {

            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}