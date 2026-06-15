package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.dao.AppartementDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.Appartement;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/formulaire-logement")
public class AfficherFormulaireLogementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = null;

        try {
            em = JpaUtil.getEntityManagerFactory().createEntityManager();

            EtudiantDAO etudiantDAO = new EtudiantDAO(em);
            AppartementDAO appartementDAO = new AppartementDAO(em);

            String location = request.getParameter("location");

            // ✅ Charger les étudiants
            List<Etudiant> etudiants =
                    etudiantDAO.listerEtudiantsNonLogesParLocation(location);

            // ✅ CORRECTION ICI (listerTous au lieu de findAll)
            List<Appartement> appartements =
                    appartementDAO.listerTous();

            // ✅ Envoyer les données à la JSP
            request.setAttribute("etudiants", etudiants);
            request.setAttribute("appartements", appartements);
            request.setAttribute("location", location);

            request.getRequestDispatcher("/pages/AjouterLogement.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors du chargement du formulaire logement", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}