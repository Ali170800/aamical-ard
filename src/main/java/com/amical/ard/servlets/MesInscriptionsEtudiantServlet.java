package com.amical.ard.servlets;

import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/etudiant/mes-inscriptions")
public class MesInscriptionsEtudiantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Etudiant etudiant = (session != null) ? (Etudiant) session.getAttribute("etudiantConnecte") : null;

        if (etudiant == null) {
            response.sendRedirect(request.getContextPath() + "/pages/connexionEtudiant.jsp");
            return;
        }

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            ParticipantCaravaneDAO dao = new ParticipantCaravaneDAO(em);
            List<ParticipantCaravane> inscriptions = dao.trouverParEmail(etudiant.getEmail());

            // ✅ TRI : Les inscriptions les plus récentes en premier
            inscriptions.sort(Comparator.comparing(ParticipantCaravane::getDateInscription,
                    Comparator.nullsLast(Comparator.reverseOrder())));

            request.setAttribute("inscriptions", inscriptions);
            request.getRequestDispatcher("/pages/etudiant/mes-inscriptions.jsp").forward(request, response);

        } finally {
            em.close();
        }
    }
}