<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sélectionner un mandat du bureau</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f2f2f2;
            margin: 20px;
        }
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 90%;
            margin: auto;
            margin-bottom: 20px;
        }
        h2 {
            color: #004d99;
            margin: 0;
        }
        .btn-bar {
            display: flex;
            gap: 10px;
        }
        .btn {
            background-color: #004d99;
            color: white;
            padding: 10px 16px;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }
        .btn:hover {
            background-color: #003366;
        }
        table {
            width: 60%;
            margin: auto;
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
    </style>
</head>
<body>

<div class="header-container">
    <h2>🔎 Choisissez un mandat pour voir les membres</h2>
    <div class="btn-bar">
        <a href="${pageContext.request.contextPath}/pages/cahier.jsp" class="btn">🔙 Retour au menu</a>
        <a href="${pageContext.request.contextPath}/bureau/ajouter" class="btn">➕ Ajouter un membre</a>
    </div>
</div>

<table>
    <thead>
        <tr>
            <th>Année de mandat</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="annee" items="${anneesMandat}">
        <tr>
            <td>${annee}</td>
            <td>
                <a href="${pageContext.request.contextPath}/bureau/membres?annee=${annee}" class="btn">
                    Voir les membres
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>