<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Créer votre mot de passe - Amicale ARD</title>
    <script src="https://cdn.tailwindcss.com"></script>

    <script>
        function preventDoubleSubmit(form) {
            const btn = form.querySelector("button");
            btn.disabled = true;
            btn.innerText = "Activation en cours...";
            return true;
        }
    </script>

    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #e0f2f1, #b2dfdb);
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
    <h2 class="text-2xl font-bold text-teal-700 mb-2">🔐 Créer votre mot de passe</h2>
    <p class="mb-6">Pour l'email : <strong>${email}</strong></p>

    <% if (request.getAttribute("erreur") != null) { %>
        <div class="bg-red-100 text-red-700 p-4 rounded-xl mb-6">
            <%= request.getAttribute("erreur") %>
        </div>
    <% } %>

    <form method="post"
          action="${pageContext.request.contextPath}/etudiant/creer-motdepasse"
          onsubmit="return preventDoubleSubmit(this);">

        <input type="hidden" name="email" value="${email}">

        <div class="mb-4">
            <input type="password" name="motDePasse" placeholder="Nouveau mot de passe"
                   class="w-full px-5 py-4 border border-teal-300 rounded-2xl focus:outline-none focus:border-teal-500" required>
        </div>

        <div class="mb-6">
            <input type="password" name="confirmMotDePasse" placeholder="Confirmer le mot de passe"
                   class="w-full px-5 py-4 border border-teal-300 rounded-2xl focus:outline-none focus:border-teal-500" required>
        </div>

        <button type="submit"
                class="w-full bg-teal-600 hover:bg-teal-700 text-white font-bold py-4 rounded-2xl text-lg">
            Activer mon compte
        </button>
    </form>
</div>

</body>
</html>