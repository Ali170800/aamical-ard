<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.amical.ard.entites.Activite" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>📋 Liste des Activités</title>
    <style>
        body {
            background: #f4f7fb;
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            padding: 30px;
        }

        h2 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
        }

        .btns {
            display: flex;
            justify-content: space-between;
            max-width: 800px;
            margin: 0 auto 25px;
        }

        .btn {
            background: #e2e8f0;
            color: #1a202c;
            padding: 10px 16px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            transition: background 0.3s ease;
        }

        .btn:hover {
            background: #cbd5e0;
        }

        table {
            width: 100%;
            max-width: 800px;
            margin: 0 auto;
            border-collapse: collapse;
            background: #ffffff;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.06);
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            padding: 14px 16px;
            text-align: left;
            border-bottom: 1px solid #e5e7eb;
        }

        th {
            background-color: #3b82f6;
            color: white;
            font-size: 15px;
        }

        td {
            font-size: 14px;
            color: #333;
        }

        tr:hover {
            background-color: #f1f5f9;
        }

        .actions a {
            margin-right: 10px;
            color: #0066cc;
            text-decoration: none;
            font-weight: 500;
        }

        .actions a:hover {
            text-decoration: underline;
        }

        .no-activities {
            text-align: center;
            color: #888;
            padding: 20px;
        }
    </style>
</head>
<body>

<h2>📋 Liste des Activités</h2>

<div class="btns">
    <a class="btn" href="pages/cahier.jsp">🔙 Retour au menu</a>
    <a class="btn" href="ajouterActivite">➕ Ajouter une activité</a>
</div>

<c:choose>
    <c:when test="${empty activites}">
        <div class="no-activities">Aucune activité enregistrée.</div>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
                <tr>
                    <th>📝 Nom</th>
                    <th>📍 Lieu</th>
                    <th>📅 Date</th>
                    <th>⚙ Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="a" items="${activites}">
                    <tr>
                        <td>${a.nom}</td>
                        <td>${a.lieu}</td>
                        <td>${a.dateActivite}</td>
                        <td class="actions">
                            <a href="voirDepenses?id=${a.id}">💰 Dépenses</a>
                            <a href="modifierActivite?id=${a.id}">✏ Modifier</a>
                            <a href="supprimerActivite?id=${a.id}" onclick="return confirm('Confirmer la suppression ?');">🗑 Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

</body>
</html>