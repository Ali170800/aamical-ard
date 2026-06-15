package com.amical.ard.servlets;

import com.amical.ard.dao.ActiviteDAO;
import com.amical.ard.dao.DepenseActiviteDAO;
import com.amical.ard.entites.Activite;
import com.amical.ard.entites.DepenseActivite;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/ajouterDepense")
public class AjouterDepenseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String libelle = request.getParameter("libelle");
        String montantStr = request.getParameter("montant");
        String idActiviteStr = request.getParameter("idActivite");

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            BigDecimal montant = new BigDecimal(montantStr);
            int idActivite = Integer.parseInt(idActiviteStr);

            ActiviteDAO activiteDAO = new ActiviteDAO(em);
            Activite activite = activiteDAO.trouverParId(idActivite);

            if (activite == null) {
                tx.rollback();
                request.setAttribute("erreur", "Activité introuvable !");
                request.getRequestDispatcher("/pages/AjouterDepense.jsp").forward(request, response);
                return;
            }

            DepenseActivite depense = new DepenseActivite();
            depense.setLibelle(libelle);
            depense.setMontant(montant);
            depense.setActivite(activite);

            DepenseActiviteDAO depenseDAO = new DepenseActiviteDAO(em);
            depenseDAO.ajouter(depense);

            tx.commit();

            // ✅ Redirection vers la liste des dépenses de cette activité
            response.sendRedirect("voirDepenses?id=" + idActivite);

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur lors de l'ajout de la dépense.");
            request.getRequestDispatcher("/pages/AjouterDepense.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }
}