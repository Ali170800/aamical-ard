package com.amical.ard.servlets;

import com.amical.ard.dao.CaravaneDAO;
import com.amical.ard.dao.ParticipantCaravaneDAO;
import com.amical.ard.entites.Caravane;
import com.amical.ard.entites.ParticipantCaravane;
import com.amical.ard.utils.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/participants/caravane")
public class AfficherParticipantsCaravaneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/participants/selectionner");
            return;
        }

        int caravaneId;
        try {
            caravaneId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/participants/selectionner");
            return;
        }

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        CaravaneDAO caravaneDAO = new CaravaneDAO(em);
        ParticipantCaravaneDAO participantDAO = new ParticipantCaravaneDAO(em); // Ajouté pour plus de cohérence

        try {
            Caravane caravane = caravaneDAO.trouverParId(caravaneId);

            if (caravane == null) {
                response.sendRedirect(request.getContextPath() + "/participants/selectionner");
                return;
            }

            // Requête améliorée et plus fiable
            List<ParticipantCaravane> participants = em.createQuery(
                            "SELECT p FROM ParticipantCaravane p " +
                                    "WHERE p.caravane.id = :caravaneId " +
                                    "ORDER BY p.nom, p.prenom",
                            ParticipantCaravane.class)
                    .setParameter("caravaneId", caravaneId)
                    .getResultList();

            // Calcul du montant total
            double montantTotal = participants.stream()
                    .mapToDouble(p -> p.getMontantPaye() != null ? p.getMontantPaye() : 0)
                    .sum();

            request.setAttribute("caravane", caravane);
            request.setAttribute("participants", participants);
            request.setAttribute("montantTotal", montantTotal);

            request.getRequestDispatcher("/pages/listeParticipants.jsp").forward(request, response);

        } finally {
            em.close();
        }
    }
}