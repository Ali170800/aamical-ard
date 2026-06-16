<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Code d'Activation - Amicale AERD</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gradient-to-br from-slate-900 to-indigo-950 min-h-screen flex items-center justify-center p-4">

<div class="max-w-md w-full bg-white rounded-3xl shadow-2xl overflow-hidden">

    <div class="bg-emerald-700 text-white p-8 text-center">
        <h1 class="text-3xl font-bold">Vérification</h1>
        <p class="text-emerald-100 mt-2">Entrez le code reçu par email</p>
    </div>

    <div class="p-8">

        <% if (request.getAttribute("erreur") != null) { %>
            <div class="bg-red-100 text-red-700 p-4 rounded-2xl mb-6 text-sm">
                <%= request.getAttribute("erreur") %>
            </div>
        <% } %>

        <% if (request.getAttribute("success") != null) { %>
            <div class="bg-green-100 text-green-700 p-4 rounded-2xl mb-6 text-sm">
                <%= request.getAttribute("success") %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/admin/verifier-code" method="post">

            <input type="hidden" name="email" value="<%= request.getAttribute("email") %>">

            <div class="mb-8">
                <input type="text" name="code" maxlength="6" required
                       class="w-full text-center text-4xl tracking-widest p-4 border rounded-2xl"
                       placeholder="123456">
            </div>

            <button type="submit"
                    class="w-full bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-4 rounded-2xl">
                Valider le Code
            </button>

        </form>

        <div class="text-center mt-6 text-xs text-slate-500">
            Le code expire dans 10 minutes
        </div>

        <div class="text-center mt-6">
            <a href="${pageContext.request.contextPath}/login.jsp"
               class="text-slate-500 hover:text-slate-700 text-sm">
                ← Retour connexion
            </a>
        </div>

    </div>
</div>

</body>
</html>