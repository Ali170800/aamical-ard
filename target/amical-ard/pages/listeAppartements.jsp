<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.amical.ard.entites.Appartement" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>Liste des Appartements</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eef2f3;
            padding: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .btn {
            padding: 8px 15px;
            text-decoration: none;
            border-radius: 4px;
            color: white;
            font-size: 14px;
        }

        .btn-modifier {
            background-color: #ffc107;
        }

        .btn-supprimer {
            background-color: #dc3545;
        }

        .btn-ajouter {
            background-color: #28a745;
        }

        .top-buttons {
            display: flex;
            justify-content: flex-end;
            margin-bottom: 15px;
        }

        .top-buttons a {
            margin-left: 10px;
        }
    </style>
</head>
<body>

    <div style="display: flex; justify-content: space-between; align-items: center;">
        <h2>📋 Liste des Appartements</h2>
        <div class="top-buttons">
            <a href="<%= request.getContextPath() %>/pages/jouter-appartement.jsp" class="btn btn-ajouter">➕ Ajouter un Appartement</a>

            <!-- ✅ Bouton corrigé -->
            <a href="<%= request.getContextPath() %>/redirect-to-dashboard" class="btn btn-retour">🔙 Retour au Menu</a>
        </div>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Adresse</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Appartement> appartements = (List<Appartement>) request.getAttribute("appartements");
                if (appartements != null) {
                    for (Appartement a : appartements) {
            %>
                <tr>
                    <td><%= a.getId() %></td>
                    <td><%= a.getNomAppartement() %></td>
                    <td><%= a.getDescription() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/modifierAppartement?id=<%= a.getId() %>" class="btn btn-modifier">✏ Modifier</a>
                        <a href="<%= request.getContextPath() %>/supprimerAppartement?id=<%= a.getId() %>" class="btn btn-supprimer" onclick="return confirm('Supprimer cet appartement ?');">🗑 Supprimer</a>
                    </td>
                </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>

</body>
</html>