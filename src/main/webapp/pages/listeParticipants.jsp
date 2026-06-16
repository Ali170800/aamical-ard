<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Participants de la caravane</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f2f2;
            margin: 20px;
        }

        h2, h3 {
            color: #333;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
            margin-top: 15px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: center;
        }

        th {
            background-color: #004d99;
            color: #fff;
        }

        tr:nth-child(even) {
            background: #f9f9f9;
        }

        .btn {
            padding: 6px 12px;
            text-decoration: none;
            color: white;
            border-radius: 4px;
        }

        .edit { background: #4CAF50; }
        .delete { background: #f44336; }

        .statut-paye {
            color: green;
            font-weight: bold;
        }

        .statut-impaye {
            color: red;
            font-weight: bold;
        }

        .total {
            font-size: 18px;
            font-weight: bold;
            margin-top: 20px;
        }

        .back {
            margin-top: 20px;
            display: inline-block;
            background: #004d99;
            color: white;
            padding: 10px 16px;
            text-decoration: none;
            border-radius: 6px;
        }

        .pdf-btn {
            display: inline-block;
            background: #007bff;
            color: white;
            padding: 10px 16px;
            text-decoration: none;
            border-radius: 6px;
            margin-top: 15px;
            border: none;
            cursor: pointer;
        }

        .pdf-btn:hover { background: #0056b3; }
        .back:hover { background: #003366; }
    </style>
</head>

<body>

<h2>Participants de la caravane : ${caravane.nom}</h2>
<h3>Date : ${caravane.date} | Montant fixé : ${caravane.montant} FCFA</h3>

<p class="total">
    💰 Montant total encaissé : ${montantTotal} FCFA<br>
    👥 Nombre de participants : ${participants != null ? participants.size() : 0}
</p>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Numéro de Chaise</th>
            <th>Caravane</th>
            <th>Date</th>
            <th>Montant payé</th>
            <th>Statut</th>
            <th>Actions</th>
        </tr>
    </thead>

    <tbody>
        <c:forEach var="participant" items="${participants}">
            <tr>
                <td>${participant.id}</td>
                <td>${participant.nom}</td>
                <td>${participant.prenom}</td>

                <td>
                    <strong>
                        <c:choose>
                            <c:when test="${not empty participant.numeroChaise}">
                                ${participant.numeroChaise}
                            </c:when>
                            <c:otherwise>
                                Non assigné
                            </c:otherwise>
                        </c:choose>
                    </strong>
                </td>

                <td>${caravane.nom}</td>
                <td>${caravane.date}</td>
                <td>${participant.montantPaye}</td>

                <td class="${participant.statutPaiement == 'PAYE' ? 'statut-paye' : 'statut-impaye'}">
                    ${participant.statutPaiement}
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/participant/modifier?id=${participant.id}"
                       class="btn edit">
                        Modifier
                    </a>

                    <a href="${pageContext.request.contextPath}/participant/supprimer?id=${participant.id}"
                       class="btn delete"
                       onclick="return confirm('Supprimer ce participant ?')">
                        Supprimer
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<!-- 📄 PDF -->
<form method="get" action="${pageContext.request.contextPath}/participants/pdf">
    <input type="hidden" name="id" value="${caravane.id}">
    <button type="submit" class="pdf-btn">
        📄 Générer le PDF de cette caravane
    </button>
</form>

<!-- 🔥 CORRECTION ICI -->
<a href="${pageContext.request.contextPath}/redirect-to-dashboard" class="back">
    ⬅ Retour au menu
</a>

</body>
</html>