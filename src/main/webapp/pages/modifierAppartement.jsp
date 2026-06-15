<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Modifier un appartement</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            padding: 30px;
        }

        h2 {
            color: #333;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            max-width: 500px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #1976d2;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #135ba1;
        }

        a {
            display: inline-block;
            margin-top: 20px;
            color: #1976d2;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Modifier un appartement</h2>

<form action="<%= request.getContextPath() %>/modifierAppartement" method="post">
    <input type="hidden" name="id" value="${appartement.id}"/>

    <label for="nomAppartement">Nom :</label>
    <input type="text" name="nomAppartement" id="nomAppartement" value="${appartement.nomAppartement}" required>

    <label for="description">Adresse :</label>
    <textarea name="description" id="description" required>${appartement.description}</textarea>

    <input type="submit" value="Enregistrer">
</form>

<a href="<%= request.getContextPath() %>/listeAppartements">⬅ Retour à la liste</a>

</body>
</html>