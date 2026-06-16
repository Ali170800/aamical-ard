<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amical.ard.entites.Etudiant" %>
<%@ page import="com.amical.ard.entites.Appartement" %>

<%
    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
    List<Appartement> appartements = (List<Appartement>) request.getAttribute("appartements");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Loger des étudiants</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #e0f7fa, #ffffff);
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 900px;
            margin: 40px auto;
            background: white;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            padding: 30px;
        }
        h2 {
            text-align: center;
            color: #00796b;
            margin-bottom: 25px;
        }
        .search-box {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 16px;
        }
        .students-list {
            max-height: 400px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 10px;
            background: #f9f9f9;
        }
        .student-item {
            padding: 12px;
            margin: 6px 0;
            background: white;
            border-radius: 6px;
            border: 1px solid #eee;
        }
        .student-item label {
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 12px;
            font-size: 16px;
        }
        .name {
            font-weight: bold;
            color: #222;
        }
        .phone {
            color: #555;
            font-weight: normal;
        }
        select {
            width: 100%;
            padding: 12px;
            border-radius: 6px;
            border: 1px solid #ccc;
            margin: 15px 0;
        }
        input[type="submit"] {
            width: 100%;
            padding: 14px;
            background-color: #009688;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 20px;
        }
        input[type="submit"]:hover {
            background-color: #00796b;
        }
        .top-button {
            float: right;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="top-button">
    <!-- Bouton corrigé : Redirige selon le rôle de l'utilisateur connecté -->
    <a href="<%= request.getContextPath() %>/redirect-to-dashboard"
       style="padding:10px 20px; background:#00796b; color:white; text-decoration:none; border-radius:8px;">
        ← Retour au menu
    </a>
</div>

<div class="container">
    <h2>🛏 Loger des étudiants</h2>

    <form method="post" action="ajouter-logement">
        <!-- Recherche dynamique -->
        <input type="text" id="searchInput" class="search-box"
               placeholder="Rechercher par nom, prénom ou téléphone..." onkeyup="filterStudents()">

        <!-- Liste des étudiants -->
        <label><strong>Sélectionnez les étudiants à loger :</strong></label>
        <div class="students-list" id="studentsList">
            <% if (etudiants != null) {
                for (Etudiant e : etudiants) { %>
                    <div class="student-item">
                        <label>
                            <input type="checkbox" name="etudiantIds" value="<%= e.getId() %>">
                            <span class="name"><%= e.getPrenom() %> <%= e.getNom() %></span>
                            <span class="phone">
                                <%= e.getTelephone() != null && !e.getTelephone().trim().isEmpty() ? e.getTelephone() : "N/A" %>
                            </span>
                        </label>
                    </div>
            <% }
            } %>
        </div>

        <label for="appartementId"><strong>Choisir l'appartement :</strong></label>
        <select name="appartementId" id="appartementId" required>
            <option value="">-- Sélectionner un appartement --</option>
            <% if (appartements != null) {
                for (Appartement a : appartements) { %>
                    <option value="<%= a.getId() %>">
                        <%= a.getNomAppartement() %>
                        <% if (a.getDescription() != null && !a.getDescription().trim().isEmpty()) { %>
                            - <%= a.getDescription() %>
                        <% } %>
                    </option>
            <% }
            } %>
        </select>

        <input type="submit" value="✅ Loger les étudiants sélectionnés">
    </form>
</div>

<script>
function filterStudents() {
    const input = document.getElementById("searchInput").value.toLowerCase();
    const items = document.querySelectorAll(".student-item");

    items.forEach(item => {
        const text = item.textContent.toLowerCase();
        item.style.display = text.includes(input) ? "block" : "none";
    });
}
</script>
</body>
</html>