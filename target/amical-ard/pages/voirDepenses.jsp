<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.amical.ard.entites.DepenseActivite" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>💰 Dépenses de l'Activité</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f7fb;
            margin: 0;
            padding: 40px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .container {
            background: #fff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.05);
            width: 100%;
            max-width: 800px;
        }

        h2, h3, h4 {
            text-align: center;
            color: #004085;
        }

        p {
            font-size: 15px;
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ccc;
            text-align: left;
        }

        th {
            background-color: #e2e8f0;
            color: #1a202c;
        }

        tr:nth-child(even) {
            background-color: #f9fafb;
        }

        tr:hover {
            background-color: #eef1f5;
        }

        .actions a {
            text-decoration: none;
            color: #c0392b;
            font-weight: bold;
        }

        .buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
            flex-wrap: wrap;
            gap: 10px;
        }

        .btn {
            background-color: #e2e8f0;
            color: #1a202c;
            padding: 10px 18px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            transition: background 0.3s ease;
        }

        .btn:hover {
            background-color: #cbd5e0;
        }

        .total {
            text-align: right;
            font-size: 16px;
            font-weight: bold;
            margin-top: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>💰 Dépenses liées à l’activité</h2>

    <p><strong>📝 Nom :</strong> ${activite.nom}</p>
    <p><strong>📍 Lieu :</strong> ${activite.lieu}</p>
    <p><strong>📅 Date :</strong> ${activite.dateActivite}</p>

    <h3>📑 Liste des Dépenses</h3>

    <table>
        <tr>
            <th>🏷 Libellé</th>
            <th>💵 Montant</th>
            <th>🛠 Actions</th>
        </tr>
        <c:forEach var="d" items="${depenses}">
            <tr>
                <td>${d.libelle}</td>
                <td>${d.montant} FCFA</td>
                <td class="actions">
                    <a href="supprimerDepense?id=${d.id}&idActivite=${activite.id}" 
                       onclick="return confirm('Voulez-vous vraiment supprimer cette dépense ?');">🗑 Supprimer</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div class="total">🧾 Total : ${total} FCFA</div>

    <div class="buttons">
        <a class="btn" href="formulaireDepense?idActivite=${activite.id}">➕ Ajouter une Dépense</a>
        <a class="btn" href="genererPDF?idActivite=${activite.id}" target="_blank">📄 Télécharger le PDF</a>
        <a class="btn" href="listeActivites">🔙 Retour aux Activités</a>
    </div>
</div>

</body>
</html>