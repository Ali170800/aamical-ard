package com.amical.ard.servlets;

import com.amical.ard.dao.EtudiantDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.utils.EntityManagerHelper;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/etudiants")
public class EtudiantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = EntityManagerHelper.getEntityManager();
        EtudiantDAO etudiantDAO = new EtudiantDAO(em);

        // Récupération des critères de recherche
        String id = request.getParameter("id");
        String prenom = request.getParameter("prenom");
        String telephone = request.getParameter("telephone");

        // Liste complète (tous les étudiants, y compris INACTIF)
        List<Etudiant> listeInitiale = etudiantDAO.listerTous();
        request.setAttribute("listeInitiale", listeInitiale);

        // Si recherche
        boolean rechercheEffectuee = (id != null && !id.trim().isEmpty()) ||
                (prenom != null && !prenom.trim().isEmpty()) ||
                (telephone != null && !telephone.trim().isEmpty());

        if (rechercheEffectuee) {
            List<Etudiant> resultatsRecherche = etudiantDAO.rechercher(id, prenom, telephone);
            request.setAttribute("resultatsRecherche", resultatsRecherche);
        }

        // Garder les valeurs dans le formulaire
        request.setAttribute("valId", id);
        request.setAttribute("valPrenom", prenom);
        request.setAttribute("valTelephone", telephone);

        em.close();
        request.getRequestDispatcher("/pages/ListeEtudiants.jsp").forward(request, response);
    }
}