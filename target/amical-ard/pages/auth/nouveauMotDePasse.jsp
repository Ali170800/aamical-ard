<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Nouveau mot de passe - Amicale AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <style>

        body {
            font-family: Arial, sans-serif;
        }

        .card-animation {
            animation: fadeIn 0.5s ease;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(15px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

    </style>

</head>

<body class="bg-gradient-to-br from-slate-900 to-indigo-950 min-h-screen flex items-center justify-center p-4">

<div class="w-full max-w-md bg-white rounded-3xl shadow-2xl overflow-hidden card-animation">

    <!-- HEADER -->
    <div class="bg-indigo-700 text-white p-8 text-center">

        <h1 class="text-3xl font-bold">
            Amicale AERD
        </h1>

        <p class="text-indigo-100 mt-2">
            Création du nouveau mot de passe
        </p>

    </div>

    <!-- FORM -->
    <div class="p-8">

        <!-- Message erreur -->
        <c:if test="${not empty erreur}">

            <div class="bg-red-100 border border-red-400 text-red-700 px-5 py-4 rounded-2xl mb-6">

                ❌ ${erreur}

            </div>

        </c:if>

        <!-- Message succès -->
        <c:if test="${not empty success}">

            <div class="bg-green-100 border border-green-400 text-green-700 px-5 py-4 rounded-2xl mb-6">

                ✅ ${success}

            </div>

        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/changerMotDePasse">

            <!-- Nouveau mot de passe -->
            <div class="mb-5">

                <label class="block text-slate-700 font-semibold mb-2">
                    Nouveau mot de passe
                </label>

                <input type="password"
                       name="motDePasse"
                       placeholder="Entrez votre nouveau mot de passe"
                       required
                       class="w-full px-4 py-3 border border-slate-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-indigo-500">

            </div>

            <!-- Confirmation -->
            <div class="mb-6">

                <label class="block text-slate-700 font-semibold mb-2">
                    Confirmer le mot de passe
                </label>

                <input type="password"
                       name="confirmation"
                       placeholder="Confirmez le mot de passe"
                       required
                       class="w-full px-4 py-3 border border-slate-300 rounded-2xl focus:outline-none focus:ring-2 focus:ring-indigo-500">

            </div>

            <!-- Bouton -->
            <button type="submit"
                    class="w-full bg-emerald-600 hover:bg-emerald-700 transition duration-300 text-white font-bold py-4 rounded-2xl shadow-lg">

                Modifier le mot de passe

            </button>

        </form>

        <!-- Retour -->
        <div class="text-center mt-6">

            <a href="${pageContext.request.contextPath}/login.jsp"
               class="text-indigo-600 hover:text-indigo-800 font-semibold">

                ← Retour à la connexion

            </a>

        </div>

    </div>

</div>

</body>
</html>