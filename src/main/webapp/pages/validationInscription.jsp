<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Validation Inscription - Amicale ARD</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f0f8f0, #e8f5e9);
            margin: 0;
            padding: 40px;
        }
        .container {
            max-width: 520px;
            margin: 0 auto;
            background: white;
            padding: 50px 40px;
            border-radius: 16px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
            text-align: center;
        }
        h2 {
            color: #00695c;
            margin-bottom: 10px;
        }
        .success {
            color: #2e7d32;
            background: #e8f5e9;
            padding: 12px;
            border-radius: 8px;
            margin: 15px 0;
        }
        .error {
            color: #c62828;
            background: #ffebee;
            padding: 12px;
            border-radius: 8px;
            margin: 15px 0;
        }
        input[type="text"] {
            width: 100%;
            padding: 16px;
            font-size: 20px;
            margin: 20px 0;
            border-radius: 8px;
            border: 2px solid #00796b;
            text-align: center;
            letter-spacing: 8px;
            font-weight: bold;
        }
        button {
            padding: 16px 50px;
            background: #009688;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            cursor: pointer;
            margin-top: 10px;
        }
        button:hover {
            background: #00796b;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Validation de votre compte</h2>

    <% if (request.getAttribute("success") != null) { %>
        <div class="success">
            <%= request.getAttribute("success") %>
        </div>
    <% } %>

    <% if (request.getAttribute("erreur") != null) { %>
        <div class="error">
            <%= request.getAttribute("erreur") %>
        </div>
    <% } %>

    <p>Un code de validation a été envoyé à <strong>${email}</strong></p>

    <form method="post" action="${pageContext.request.contextPath}/etudiant/valider">
        <input type="text"
               name="code"
               placeholder="Entrez le code à 6 chiffres"
               maxlength="6"
               required
               autofocus>

        <input type="hidden" name="email" value="${email}">

        <button type="submit">Valider mon compte</button>
    </form>

    <p style="margin-top: 25px; font-size: 14px; color: #666;">
        Vous n'avez pas reçu le code ? Vérifiez vos spams ou
        <a href="${pageContext.request.contextPath}/etudiant/inscription">réinscrire</a>
    </p>
</div>
</body>
</html>