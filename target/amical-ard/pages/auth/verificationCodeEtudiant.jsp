<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Validation du Code Étudiant - Amicale AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <style>

        body {
            font-family: 'Segoe UI', sans-serif;
        }

        .fade-animation {
            animation: fadeIn 0.6s ease;
        }

        @keyframes fadeIn {

            from {
                opacity: 0;
                transform: translateY(20px);
            }

            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .input-style {
            transition: all 0.3s ease;
        }

        .input-style:focus {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(79,70,229,0.15);
        }

        .btn-style {
            transition: all 0.3s ease;
        }

        .btn-style:hover {
            transform: translateY(-2px);
            box-shadow: 0 12px 25px rgba(79,70,229,0.25);
        }

    </style>

</head>

<body class="bg-gradient-to-br from-slate-900 via-indigo-900 to-slate-950 min-h-screen flex items-center justify-center p-4">

<div class="w-full max-w-md bg-white rounded-[30px] shadow-2xl overflow-hidden fade-animation">

    <!-- HEADER -->
    <div class="bg-gradient-to-r from-indigo-700 to-indigo-900 text-white p-10 text-center">

        <div class="text-5xl mb-4">
            📩
        </div>

        <h1 class="text-3xl font-extrabold">
            Amicale AERD
        </h1>

        <p class="text-indigo-100 mt-3">
            Validation du code étudiant
        </p>

    </div>

    <!-- CONTENU -->
    <div class="p-8">

        <!-- MESSAGE ERREUR -->
        <c:if test="${not empty erreur}">

            <div class="bg-red-100 border border-red-400 text-red-700 px-5 py-4 rounded-2xl mb-6">

                ❌ ${erreur}

            </div>

        </c:if>

        <!-- MESSAGE SUCCÈS -->
        <c:if test="${not empty success}">

            <div class="bg-green-100 border border-green-400 text-green-700 px-5 py-4 rounded-2xl mb-6">

                ✅ ${success}

            </div>

        </c:if>

        <!-- TEXTE -->
        <div class="text-center mb-6">

            <p class="text-slate-600 leading-relaxed">
                Entrez le code de vérification envoyé à votre adresse email.
            </p>

        </div>

        <!-- FORMULAIRE -->
        <form method="post"
              action="${pageContext.request.contextPath}/etudiant/verifierCodeReset">

            <div class="mb-8">

                <label class="block text-slate-700 font-semibold mb-2">

                    Code de vérification

                </label>

                <input type="text"
                       name="code"
                       placeholder="Entrez le code reçu"
                       required
                       maxlength="6"
                       class="input-style w-full px-5 py-4 border border-slate-300 rounded-2xl text-center text-2xl tracking-[10px] focus:outline-none focus:ring-2 focus:ring-indigo-500">

            </div>

            <!-- BOUTON -->
            <button type="submit"
                    class="btn-style w-full bg-gradient-to-r from-indigo-600 to-indigo-700 hover:from-indigo-700 hover:to-indigo-800 text-white font-bold py-4 rounded-2xl shadow-xl text-lg">

                Vérifier le code

            </button>

        </form>

        <!-- RETOUR -->
        <div class="text-center mt-8">

            <a href="${pageContext.request.contextPath}/pages/auth/motDePasseOublieEtudiant.jsp"
               class="text-indigo-600 hover:text-indigo-800 font-semibold">

                ← Retour

            </a>

        </div>

    </div>

</div>

</body>
</html>