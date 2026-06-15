<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Liste des Caravanes</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }

        .header-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        h2 {
            margin: 0;
        }

        .btn {
            padding: 6px 12px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }

        .btn-primary {
            background-color: #007bff;
            color: white;
        }

        .btn-primary:hover {
            background-color: #0069d9;
        }

        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
        }

        .top-actions {
            display: flex;
            gap: 10px;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        .edit {
            background-color: #4CAF50;
            color: white;
        }

        .delete {
            background-color: #f44336;
            color: white;
        }

        .message {
            background-color: #2196F3;
            color: white;
        }

        .empty {
            text-align: center;
            padding: 20px;
            color: #777;
        }
    </style>
</head>

<body>

<!-- 🔥 HEADER -->
<div class="header-row">
    <h2>Liste des Caravanes</h2>

    <div class="top-actions">

        <!-- Ajouter -->
        <a href="${pageContext.request.contextPath}/caravane/creer"
           class="btn btn-primary">
            ➕ Ajouter une caravane
        </a>

        <!-- 🔥 CORRECTION ICI -->
        <a href="${pageContext.request.contextPath}/redirect-to-dashboard"
           class="btn btn-secondary">
            ↩ Retour au menu
        </a>

    </div>
</div>

<!-- TABLEAU -->
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Date</th>
            <th>Montant</th>
            <th>Actions</th>
            <th>Message</th>
        </tr>
    </thead>

    <tbody>

        <!-- ✅ Si vide -->
        <c:if test="${empty caravanes}">
            <tr>
                <td colspan="6" class="empty">
                    Aucune caravane disponible
                </td>
            </tr>
        </c:if>

        <!-- ✅ Liste -->
        <c:forEach var="caravane" items="${caravanes}">
            <tr>
                <td>${caravane.id}</td>
                <td>${caravane.nom}</td>
                <td>${caravane.date}</td>
                <td>${caravane.montant}</td>

                <td>
                    <a href="${pageContext.request.contextPath}/caravane/modifier?id=${caravane.id}"
                       class="btn edit">
                        Modifier
                    </a>

                    <a href="${pageContext.request.contextPath}/caravane/supprimer?id=${caravane.id}"
                       class="btn delete"
                       onclick="return confirm('Voulez-vous vraiment supprimer cette caravane ?');">
                        Supprimer
                    </a>
                </td>

                <td>
                    <a href="${pageContext.request.contextPath}/caravane/envoyerMessage?id=${caravane.id}"
                       class="btn message">
                        Envoyer
                    </a>
                </td>
            </tr>
        </c:forEach>

    </tbody>
</table>

</body>
</html>