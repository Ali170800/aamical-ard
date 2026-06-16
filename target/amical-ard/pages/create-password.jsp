<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Créer mot de passe - Amicale AERD</title>
    <script src="https://cdn.tailwindcss.com"></script>

```
<script>
    function preventDoubleSubmit(form) {
        const btn = form.querySelector("button");
        btn.disabled = true;
        btn.innerText = "Activation en cours...";
        return true;
    }
</script>
```

</head>

<body class="bg-gradient-to-br from-indigo-900 to-slate-900 min-h-screen flex items-center justify-center p-4">

<div class="max-w-md w-full bg-white rounded-3xl shadow-2xl overflow-hidden">

```
<!-- HEADER -->
<div class="bg-indigo-700 text-white p-8 text-center">
    <h1 class="text-3xl font-bold">🔐 Création du mot de passe</h1>
    <p class="text-indigo-100 mt-2">Activez votre compte admin</p>
</div>

<div class="p-8">

    <% if (request.getAttribute("erreur") != null) { %>
        <div class="bg-red-100 text-red-700 p-4 rounded-2xl mb-6 text-sm">
            <%= request.getAttribute("erreur") %>
        </div>
    <% } %>

    <p class="text-sm text-slate-500 mb-6 text-center">
        Email : <strong>${email}</strong>
    </p>

    <!-- FORMULAIRE -->
    <form action="${pageContext.request.contextPath}/admin/activer"
          method="post"
          onsubmit="return preventDoubleSubmit(this);">

        <!-- EMAIL CACHÉ -->
        <input type="hidden" name="email" value="${email}">

        <div class="mb-5">
            <label class="block text-slate-600 mb-2 font-medium">Mot de passe</label>
            <input type="password" name="motDePasse" required
                   class="w-full px-4 py-3 border border-slate-300 rounded-2xl">
        </div>

        <div class="mb-6">
            <label class="block text-slate-600 mb-2 font-medium">Confirmer mot de passe</label>
            <input type="password" name="confirmMotDePasse" required
                   class="w-full px-4 py-3 border border-slate-300 rounded-2xl">
        </div>

        <button type="submit"
                class="w-full bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-4 rounded-2xl">
            Activer mon compte
        </button>

    </form>

    <div class="text-center mt-6">
        <a href="${pageContext.request.contextPath}/login.jsp"
           class="text-slate-500 hover:text-slate-700 text-sm">
            ← Retour à la connexion
        </a>
    </div>

</div>
```

</div>

</body>
</html>
