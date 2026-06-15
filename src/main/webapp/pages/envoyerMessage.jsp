<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>✉ Envoyer un message</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f5f7fa;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 60px auto;
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
        }

        label {
            display: block;
            margin-top: 15px;
            color: #34495e;
            font-weight: bold;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 14px;
        }

        textarea {
            resize: vertical;
        }

        .btn-submit {
            margin-top: 25px;
            background-color: #2ecc71;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }

        .btn-submit:hover {
            background-color: #27ae60;
        }

        .message {
            margin-top: 20px;
            text-align: center;
            font-weight: bold;
        }

        .message.success {
            color: #27ae60;
        }

        .message.error {
            color: #c0392b;
        }

        .btn-retour {
            display: block;
            text-align: center;
            margin-top: 30px;
            text-decoration: none;
            background-color: #3498db;
            color: white;
            padding: 10px 18px;
            border-radius: 6px;
            font-weight: bold;
        }

        .btn-retour:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>📩 Envoyer un message pour la caravane</h2>

    <form action="${pageContext.request.contextPath}/caravane/envoyerMessage" method="post">
        <input type="hidden" name="id" value="${param.id}"/>

        <label for="sujet">Sujet :</label>
        <input type="text" name="sujet" id="sujet" required>

        <label for="message">Message :</label>
        <textarea name="message" id="message" rows="6" required></textarea>

        <button class="btn-submit" type="submit">Envoyer à tous les étudiants</button>
    </form>

    <c:if test="${not empty message}">
        <div class="message success">${message}</div>
    </c:if>

    <c:if test="${not empty erreur}">
        <div class="message error">${erreur}</div>
    </c:if>

    <a class="btn-retour" href="${pageContext.request.contextPath}/caravane/lister">⬅ Retour à la liste des caravanes</a>
</div>

</body>
</html>