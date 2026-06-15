package com.amical.ard.servlets;

import com.amical.ard.dao.*;
import com.amical.ard.entites.*;
import com.amical.ard.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/api/recherche")
public class GlobalSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("q");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (query == null || query.trim().isEmpty()) {
            response.getWriter().write("[]");
            return;
        }

        query = query.trim().toLowerCase();
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        List<Map<String, Object>> results = new ArrayList<>();

        try {
            // 1. Recherche Étudiants
            List<Etudiant> etudiants = em.createQuery(
                            "SELECT e FROM Etudiant e WHERE LOWER(e.nom) LIKE :q OR LOWER(e.prenom) LIKE :q " +
                                    "OR LOWER(e.email) LIKE :q OR e.telephone LIKE :q", Etudiant.class)
                    .setParameter("q", "%" + query + "%")
                    .setMaxResults(8)
                    .getResultList();

            for (Etudiant e : etudiants) {
                Map<String, Object> item = new HashMap<>();
                item.put("type", "etudiant");
                item.put("id", e.getId());
                item.put("titre", e.getPrenom() + " " + e.getNom());
                item.put("sousTitre", e.getEmail() != null ? e.getEmail() : e.getTelephone());
                item.put("url", request.getContextPath() + "/etudiants?id=" + e.getId());
                results.add(item);
            }

            // 2. Recherche Membres du Bureau
            List<Bureau> bureaux = em.createQuery(
                            "SELECT b FROM Bureau b WHERE LOWER(b.nom) LIKE :q OR LOWER(b.prenom) LIKE :q " +
                                    "OR LOWER(b.roleBureau) LIKE :q OR LOWER(b.email) LIKE :q", Bureau.class)
                    .setParameter("q", "%" + query + "%")
                    .setMaxResults(5)
                    .getResultList();

            for (Bureau b : bureaux) {
                Map<String, Object> item = new HashMap<>();
                item.put("type", "bureau");
                item.put("id", b.getId());
                item.put("titre", b.getPrenom() + " " + b.getNom());
                item.put("sousTitre", b.getRoleBureau());
                item.put("url", request.getContextPath() + "/bureau/selectionner");
                results.add(item);
            }

            // 3. Recherche Caravanes
            List<Caravane> caravanes = em.createQuery(
                            "SELECT c FROM Caravane c WHERE LOWER(c.nom) LIKE :q", Caravane.class)
                    .setParameter("q", "%" + query + "%")
                    .setMaxResults(5)
                    .getResultList();

            for (Caravane c : caravanes) {
                Map<String, Object> item = new HashMap<>();
                item.put("type", "caravane");
                item.put("id", c.getId());
                item.put("titre", c.getNom());
                item.put("sousTitre", "Caravane - " + c.getDate());
                item.put("url", request.getContextPath() + "/participants/caravane?id=" + c.getId());
                results.add(item);
            }

        } finally {
            em.close();
        }

        // Conversion en JSON simple (sans bibliothèque externe)
        String json = convertToJson(results);
        response.getWriter().write(json);
    }

    private String convertToJson(List<Map<String, Object>> list) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> m = list.get(i);
            sb.append("{")
                    .append("\"type\":\"").append(m.get("type")).append("\",")
                    .append("\"id\":").append(m.get("id")).append(",")
                    .append("\"titre\":\"").append(escapeJson(m.get("titre").toString())).append("\",")
                    .append("\"sousTitre\":\"").append(escapeJson(m.get("sousTitre").toString())).append("\",")
                    .append("\"url\":\"").append(escapeJson(m.get("url").toString())).append("\"")
                    .append("}");
            if (i < list.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    private String escapeJson(String str) {
        return str.replace("\"", "\\\"").replace("\n", "\\n");
    }
}