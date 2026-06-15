<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vérification du Code - Amicale AERD</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #e8f5e9, #c8e6c9);
            margin: 0;
            padding: 40px;
        }
        .container {
            max-width: 480px;
            margin: 0 auto;
            background: white;
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            text-align: center;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="text-2xl font-bold text-green-700 mb-2">🔢 Vérification du code</h2>
    <p class="mb-6">Un code a été envoyé à <strong>${email}</strong></p>

    <% if (request.getAttribute("erreur") != null) { %>
        <div class="bg-red-100 text-red-700 p-4 rounded-xl mb-6">
            <%= request.getAttribute("erreur") %>
        </div>
    <% } %>

    <% if (request.getAttribute("success") != null) { %>
        <div class="bg-green-100 text-green-700 p-4 rounded-xl mb-6">
            <%= request.getAttribute("success") %>
        </div>
    <% } %>

    <form method="post" action="${pageContext.request.contextPath}/etudiant/valider">
        <input type="hidden" name="email" value="${email}">

        <input type="text" name="code" maxlength="6"
               placeholder="123456"
               class="w-full text-center text-4xl tracking-widest border-2 border-green-500 rounded-2xl py-6 focus:outline-none focus:border-green-600"
               required autofocus>

        <button type="submit"
                class="w-full mt-6 bg-green-600 hover:bg-green-700 text-white font-bold py-4 rounded-2xl text-lg">
            Valider le code
        </button>
    </form>

    <p class="mt-6 text-sm text-gray-500">
        Vous n'avez pas reçu le code ?
        <a href="${pageContext.request.contextPath}/pages/activationCompte.jsp" class="text-green-600 hover:underline">Renvoyer le code</a>
    </p>
</div>

</body>
</html>