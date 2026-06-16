<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">

    <title>Validation du code - Amicale AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <style>

        body {
            font-family: Arial, sans-serif;
        }

        .glass {
            backdrop-filter: blur(10px);
            background: rgba(255,255,255,0.95);
        }

    </style>

</head>

<body class="bg-gradient-to-br from-slate-900 via-indigo-900 to-slate-950 min-h-screen flex items-center justify-center p-4">

<div class="w-full max-w-md">

    <!-- Carte -->
    <div class="glass rounded-3xl shadow-2xl overflow-hidden">

        <!-- Header -->
        <div class="bg-indigo-700 text-white text-center p-8">

            <h1 class="text-3xl font-bold">
                Amicale AERD
            </h1>

            <p class="mt-2 text-indigo-100">
                Validation du code de récupération
            </p>

        </div>

        <!-- Contenu -->
        <div class="p-8">

            <!-- Message erreur -->
            <c:if test="${not empty sessionScope.erreur}">

                <div class="bg-red-100 border border-red-400 text-red-700 px-5 py-4 rounded-2xl mb-6">

                    ❌ ${sessionScope.erreur}

                </div>

                <c:remove var="erreur" scope="session"/>

            </c:if>

            <!-- Message succès -->
            <c:if test="${not empty sessionScope.success}">

                <div class="bg-green-100 border border-green-400 text-green-700 px-5 py-4 rounded-2xl mb-6">

                    ✅ ${sessionScope.success}

                </div>

                <c:remove var="success" scope="session"/>

            </c:if>

            <!-- Texte -->
            <div class="text-center mb-6">

                <h2 class="text-2xl font-bold text-slate-800 mb-2">
                    Vérification du code
                </h2>

                <p class="text-slate-500 text-sm leading-relaxed">
                    Saisissez le code de validation reçu par email
                    pour continuer la récupération du compte.
                </p>

            </div>

            <!-- Formulaire -->
            <form method="post"
                  action="${pageContext.request.contextPath}/verifierCodeReset">

                <!-- Champ code -->
                <div class="mb-6">

                    <label class="block text-slate-700 font-medium mb-2">

                        Code de validation

                    </label>

                    <input type="text"
                           name="code"
                           placeholder="Ex : 458921"
                           required
                           class="w-full px-4 py-4 border border-slate-300 rounded-2xl focus:outline-none focus:ring-4 focus:ring-indigo-300 text-center text-2xl tracking-[8px] font-bold">

                </div>

                <!-- Bouton -->
                <button type="submit"
                        class="w-full bg-indigo-600 hover:bg-indigo-700 transition duration-300 text-white font-bold py-4 rounded-2xl shadow-lg">

                    Vérifier le code

                </button>

            </form>

            <!-- Retour -->
            <div class="text-center mt-6">

                <a href="${pageContext.request.contextPath}/pages/auth/motDePasseOublie.jsp"
                   class="text-indigo-600 hover:text-indigo-800 text-sm font-semibold">

                    ← Retour à la récupération

                </a>

            </div>

        </div>

    </div>

</div>

</body>
</html>