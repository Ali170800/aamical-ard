<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Sélectionner une caravane</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f2f2;
            margin: 20px;
        }

        .header-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }

        .header-bar h2 {
            color: #004d99;
            margin: 0;
        }

        .btn-container {
            display: flex;
            gap: 10px;
        }

        .btn {
            background: #004d99;
            color: white;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
            transition: background 0.3s;
        }

        .btn:hover {
            background: #003366;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: center;
        }

        th {
            background: #004d99;
            color: #fff;
        }

        tr:nth-child(even) {
            background: #f9f9f9;
        }

        .empty {
            color: red;
            font-weight: bold;
        }
    </style>
</head>

<body>

<!-- HEADER -->
<div class="header-bar">
    <h2>🔎 Sélectionnez une caravane pour voir ses participants</h2>

    <div class="btn-container">

        <!-- Ajouter -->
        <a href="${pageContext.request.contextPath}/participant/ajouter" class="btn">
            ➕ Ajouter un participant
        </a>

        <!-- 🔥 CORRECTION ICI -->
        <a href="${pageContext.request.contextPath}/redirect-to-dashboard" class="btn">
            ↩ Retour au menu
        </a>

    </div>
</div>

<!-- SI VIDE -->
<c:if test="${empty caravanes}">
    <p class="empty">❌ Aucune caravane enregistrée.</p>
</c:if>

<!-- TABLEAU -->
<c:if test="${not empty caravanes}">
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Date</th>
                <th>Montant fixé</th>
                <th>Action</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="caravane" items="${caravanes}">
                <tr>
                    <td>${caravane.id}</td>
                    <td>${caravane.nom}</td>
                    <td>${caravane.date}</td>
                    <td>${caravane.montant} FCFA</td>

                    <td>
                        <a href="${pageContext.request.contextPath}/participants/caravane?id=${caravane.id}" class="btn">
                            Voir les participants
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

</body>
</html>