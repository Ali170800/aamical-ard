package com.amical.ard.servlets;

import com.amical.ard.dao.NotificationDAO;
import com.amical.ard.entites.Etudiant;
import com.amical.ard.entites.Notification;
import com.amical.ard.utils.EntityManagerHelper;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/get-notifications")
public class GetNotificationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Etudiant etudiant = (Etudiant) (session != null ? session.getAttribute("etudiantConnecte") : null);

        if (etudiant == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Utilisation de l'EntityManager
        EntityManager em = EntityManagerHelper.getEntityManager();
        NotificationDAO dao = new NotificationDAO(em);

        // --- NETTOYAGE AUTOMATIQUE ---
        // Cette méthode supprime les notifications trop anciennes dans la base
        try {
            dao.supprimerVieillesNotifications();
        } catch (Exception e) {
            // On log l'erreur mais on ne bloque pas l'affichage des notifications
            e.printStackTrace();
        }

        // Récupération des notifications après nettoyage
        List<Notification> notifications = dao.listerPourEtudiant(etudiant.getId());

        // Construction manuelle du JSON
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < notifications.size(); i++) {
            Notification n = notifications.get(i);
            json.append("{");
            json.append("\"id\":").append(n.getId()).append(",");
            // Nettoyage : protection contre les guillemets et suppression des retours à la ligne
            String msgPropre = n.getMessage() != null ? n.getMessage().replace("\"", "\\\"").replaceAll("[\\n\\r]", " ") : "";
            json.append("\"message\":\"").append(msgPropre).append("\"");
            json.append("}");
            if (i < notifications.size() - 1) json.append(",");
        }
        json.append("]");

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json.toString());
    }
}