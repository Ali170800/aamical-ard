<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Membres du bureau</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f2f2f2; margin: 20px; }
        h2, h3 { color: #333; }
        table { width: 100%; border-collapse: collapse; background: #fff; margin-top: 15px; }
        th, td { padding: 12px; border: 1px solid #ccc; text-align: center; }
        th { background-color: #004d99; color: #fff; }
        tr:nth-child(even) { background: #f9f9f9; }
        .btn { padding: 6px 12px; text-decoration: none; color: white; border-radius: 4px; }
        .edit { background: #4CAF50; }
        .delete { background: #f44336; }
        .pdf { background: #004d99; margin-top: 15px; display: inline-block; }
        .back { background: #777; margin-top: 15px; display: inline-block; }
    </style>
</head>
<body>

<h2>📋 Membres du bureau du mandat : <span style="color:#004d99;">${annee}</span></h2>

<!-- Tableau des membres -->
<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Rôle</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="membre" items="${membres}">
        <tr>
            <td>${membre.id}</td>
            <td>${membre.nom}</td>
            <td>${membre.prenom}</td>
            <td>${membre.roleBureau}</td>
            <td>${membre.email}</td>
            <td>${membre.numero}</td>
            <td>
                <a href="${pageContext.request.contextPath}/bureau/modifier?id=${membre.id}" class="btn edit">Modifier</a>
                <a href="${pageContext.request.contextPath}/bureau/supprimer?id=${membre.id}" 
                   class="btn delete"
                   onclick="return confirm('Voulez-vous vraiment supprimer ce membre du bureau ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Boutons PDF et retour -->
<div style="margin-top:20px;">
    <a href="${pageContext.request.contextPath}/bureau/pdf?annee=${annee}" class="btn pdf">📄 Générer PDF</a>
    <a href="${pageContext.request.contextPath}/bureau/selectionner" class="btn back">⬅ Retour</a>
</div>

</body>
</html>s