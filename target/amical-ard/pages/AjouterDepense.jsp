<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.amical.ard.entites.Activite" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>➕ Ajouter une Dépense</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f4f7fb;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background: #fff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 600px;
        }

        h2 {
            text-align: center;
            color: #004085;
            margin-bottom: 25px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-top: 15px;
        }

        input[type="text"],
        input[type="number"],
        select {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 15px;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            margin-top: 25px;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .btn-links {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
            flex-wrap: wrap;
            gap: 10px;
        }

        .btn {
            background-color: #e2e8f0;
            color: #1a202c;
            padding: 10px 16px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            transition: background 0.3s ease;
        }

        .btn:hover {
            background-color: #cbd5e0;
        }

        .error {
            color: #c00;
            margin-top: 15px;
            text-align: center;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>➕ Ajouter une Dépense</h2>

    <form action="ajouterDepense" method="post">
        <label>🗂 Activité concernée :</label>
        <select name="idActivite" required>
            <option value="">-- Sélectionnez une activité --</option>
            <%
                List<Activite> activites = (List<Activite>) request.getAttribute("listeActivites");
                if (activites != null) {
                    for (Activite a : activites) {
            %>
                        <option value="<%= a.getId() %>"><%= a.getNom() %></option>
            <%
                    }
                }
            %>
        </select>

        <label>🏷 Libellé :</label>
        <input type="text" name="libelle" required />

        <label>💵 Montant :</label>
        <input type="number" name="montant" step="0.01" required />

        <input type="submit" value="Ajouter la dépense">
    </form>

    <%
        if (request.getAttribute("erreur") != null) {
    %>
        <div class="error">❌ <%= request.getAttribute("erreur") %></div>
    <%
        }
    %>

    <div class="btn-links">
        <a class="btn" href="listeActivites">📋 Liste des Activités</a>
        <a class="btn" href="pages/cahier.jsp">🔙 Retour au menu</a>
    </div>
</div>
</body>
</html>