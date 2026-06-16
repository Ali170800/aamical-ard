<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Connexion Étudiant - Amicale ARD</title>

    <style>

        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #e0f7fa, #ffffff);
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 420px;
            margin: 0 auto;
            background: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #00796b;
        }

        input {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 16px;
        }

        button {
            width: 100%;
            padding: 14px;
            background: #009688;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            cursor: pointer;
            margin-top: 15px;
        }

        button:hover {
            background: #00796b;
        }

        .error {
            color: red;
            text-align: center;
            margin: 15px 0;
            padding: 10px;
            background: #ffebee;
            border-radius: 6px;
        }

        .success {
            color: green;
            text-align: center;
            margin: 15px 0;
            padding: 10px;
            background: #e8f5e9;
            border-radius: 6px;
        }

        .link {
            text-align: center;
            margin-top: 20px;
            font-size: 15px;
        }

        a {
            text-decoration: none;
        }

        .link a {
            color: #0066cc;
            font-weight: bold;
        }

        .activate {
            color: #d32f2f !important;
        }

    </style>

</head>

<body>

<div class="container">

    <h2>🔑 Connexion Étudiant</h2>

    <!-- MESSAGE COMPTE DÉJÀ ACTIVÉ -->
    <c:if test="${param.success == 'dejaActive'}">

        <div class="success">
            ✅ Votre compte est déjà activé. Connectez-vous directement.
        </div>

    </c:if>

    <!-- MESSAGE ERREUR -->
    <c:if test="${not empty erreur}">

        <div class="error">
            ${erreur}
        </div>

    </c:if>

    <!-- MESSAGE SUCCÈS -->
    <c:if test="${not empty success}">

        <div class="success">
            ${success}
        </div>

    </c:if>

    <!-- FORMULAIRE -->
    <form method="post"
          action="${pageContext.request.contextPath}/etudiant/connexion">

        <input type="email"
               name="email"
               placeholder="Votre email"
               required>

        <input type="password"
               name="motDePasse"
               placeholder="Mot de passe"
               required>

        <button type="submit">
            Se connecter
        </button>

        <!-- MOT DE PASSE OUBLIÉ -->
        <div class="link">

            <a href="${pageContext.request.contextPath}/pages/auth/motDePasseOublieEtudiant.jsp">

                Mot de passe oublié ?

            </a>

        </div>

    </form>

    <!-- INSCRIPTION -->
    <div class="link">

        <p>Pas encore de compte ?</p>

        <a href="${pageContext.request.contextPath}/etudiant/inscription">

            ➕ Créer un compte maintenant

        </a>

    </div>

    <!-- ACTIVATION -->
    <div class="link">

        <p>Compte non activé ?</p>

        <a class="activate"
           href="${pageContext.request.contextPath}/pages/activationCompte.jsp">

            🔑 Activer mon compte

        </a>

    </div>

</div>

</body>
</html>