<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>✏ Modifier l’activité</title>
    <style>
        body {
            background-color: #f4f7fb;
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background: white;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
        }

        h2 {
            text-align: center;
            color: #004085;
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-top: 15px;
        }

        input[type="text"],
        input[type="date"] {
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
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            margin-top: 25px;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .error {
            color: #c00;
            text-align: center;
            margin-bottom: 15px;
            font-weight: bold;
        }

        .btn-link {
            display: block;
            text-align: center;
            margin-top: 25px;
        }

        .btn-link a {
            background: #e2e8f0;
            color: #1a202c;
            padding: 10px 16px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            transition: background 0.3s ease;
        }

        .btn-link a:hover {
            background: #cbd5e0;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>✏ Modifier l’activité</h2>

    <c:if test="${not empty erreur}">
        <div class="error">❌ ${erreur}</div>
    </c:if>

    <form method="post" action="modifierActivite">
        <input type="hidden" name="id" value="${activite.id}" />

        <label>📝 Nom :</label>
        <input type="text" name="nom" value="${activite.nom}" required />

        <label>📍 Lieu :</label>
        <input type="text" name="lieu" value="${activite.lieu}" required />

        <label>📅 Date :</label>
        <fmt:formatDate value="${activite.dateActivite}" pattern="yyyy-MM-dd" var="dateFormatee" />
        <input type="date" name="dateActivite" value="${dateFormatee}" required />

        <input type="submit" value="💾 Enregistrer les modifications">
    </form>

    <div class="btn-link">
        <a href="listeActivites">🔙 Retour à la liste des activités</a>
    </div>
</div>
</body>
</html>