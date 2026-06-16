<<<<<<< HEAD
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Connexion</title>
    <style>
        body {
            background-image: url('${pageContext.request.contextPath}/images/c.jpg');
            background-size: 90%; /* image de fond réduite */
            background-repeat: no-repeat;
            background-position: center;
            font-family: Arial, sans-serif;
            color: white;
        }

        .login-container {
            background-color: rgba(0, 0, 0, 0.6);
            width: 300px;
            margin: 100px auto;
            padding: 30px;
            border-radius: 10px;
            text-align: center;
        }

        .login-container img {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            border: 3px solid white;
            margin-bottom: 10px;
        }

        .app-name {
            font-size: 20px;
            margin: 15px 0; /* espace entre la photo et "Connexion" */
        }

        input[type="text"], input[type="password"] {
            width: 90%;
            padding: 10px;
            margin-bottom: 15px;
            border: none;
            border-radius: 5px;
        }

        button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            background-color: #ffffff;
            color: black;
            font-weight: bold;
            cursor: pointer;
        }

        .erreur {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>

    <div class="login-container">
        <img src="${pageContext.request.contextPath}/images/d.jpg" alt="Logo">
        
        <div class="app-name">AERD</div> <!-- Texte entre la photo et "Connexion" -->

        <h2>Connexion</h2>

        <form method="post" action="LoginServlet">
            <input type="text" name="login" placeholder="Login" required><br>
            <input type="password" name="motDePasse" placeholder="Mot de passe" required><br>
            <button type="submit">Se connecter</button>
        </form>

        <div class="erreur">
            <%= request.getAttribute("erreur") != null ? request.getAttribute("erreur") : "" %>
        </div>
    </div>

</body>
</html>
=======
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Connexion Administrateur - Amicale AERD</title>

    <script src="https://cdn.tailwindcss.com"></script>

    <style>

        .tab-active {
            border-bottom: 3px solid #4f46e5;
            color: #4f46e5;
            font-weight: 600;
        }

        .forgot-link {
            text-align: right;
            margin-top: 12px;
        }

        .forgot-link a {
            color: #4f46e5;
            font-size: 14px;
            font-weight: 600;
            text-decoration: none;
        }

        .forgot-link a:hover {
            text-decoration: underline;
        }

    </style>

</head>

<body class="bg-gradient-to-br from-slate-900 to-indigo-950 min-h-screen flex items-center justify-center p-4">

<div class="max-w-md w-full bg-white rounded-3xl shadow-2xl overflow-hidden">

    <!-- HEADER -->
    <div class="bg-indigo-700 text-white p-8 text-center">

        <h1 class="text-3xl font-bold">
            Amicale AERD
        </h1>

        <p class="text-indigo-100 mt-1">
            Espace Administrateur
        </p>

    </div>

    <!-- CONTENU -->
    <div class="p-8">

        <!-- MESSAGES -->
        <%
            String erreurMsg =
                    (String) request.getSession().getAttribute("erreur");

            String successMsg =
                    (String) request.getSession().getAttribute("success");
        %>

        <% if (erreurMsg != null) { %>

        <div class="bg-red-100 border border-red-400 text-red-700 px-5 py-4 rounded-2xl mb-6">

            ❌ <%= erreurMsg %>

        </div>

        <% request.getSession().removeAttribute("erreur"); %>

        <% } %>

        <% if (successMsg != null) { %>

        <div class="bg-green-100 border border-green-400 text-green-700 px-5 py-4 rounded-2xl mb-6">

            ✅ <%= successMsg %>

        </div>

        <% request.getSession().removeAttribute("success"); %>

        <% } %>

        <!-- ONGLETS -->
        <div class="flex border-b mb-6">

            <button
                    type="button"
                    onclick="showTab(0)"
                    id="tab1"
                    class="tab-active flex-1 py-3 text-center">

                Se Connecter

            </button>

            <button
                    type="button"
                    onclick="showTab(1)"
                    id="tab2"
                    class="flex-1 py-3 text-center text-slate-500">

                Activer Compte

            </button>

        </div>

        <!-- ===================== CONNEXION ===================== -->

        <div id="loginForm">

            <form
                    action="${pageContext.request.contextPath}/login"
                    method="post">

                <!-- LOGIN -->
                <div class="mb-5">

                    <label class="block text-slate-600 mb-2 font-medium">

                        Email ou Login

                    </label>

                    <input
                            type="text"
                            name="login"
                            required
                            class="w-full px-4 py-3 border border-slate-300 rounded-2xl">

                </div>

                <!-- MOT DE PASSE -->
                <div class="mb-4">

                    <label class="block text-slate-600 mb-2 font-medium">

                        Mot de passe

                    </label>

                    <input
                            type="password"
                            name="motDePasse"
                            required
                            class="w-full px-4 py-3 border border-slate-300 rounded-2xl">

                </div>

                <!-- MOT DE PASSE OUBLIÉ -->
                <div class="forgot-link">

                    <a href="${pageContext.request.contextPath}/pages/auth/motDePasseOublie.jsp">

                        Mot de passe oublié ?

                    </a>

                </div>

                <!-- BOUTON -->
                <button
                        type="submit"
                        class="w-full mt-6 bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-4 rounded-2xl">

                    Se connecter

                </button>

            </form>

        </div>

        <!-- ===================== ACTIVATION ===================== -->

        <div id="activationForm" class="hidden">

            <!-- ETAPE 1 -->
            <form
                    id="step1Form"
                    action="${pageContext.request.contextPath}/admin/verifier"
                    method="post">

                <div class="mb-5">

                    <label class="block text-slate-600 mb-2 font-medium">

                        Email

                    </label>

                    <input
                            type="email"
                            name="email"
                            required
                            class="w-full px-4 py-3 border border-slate-300 rounded-2xl">

                </div>

                <div class="mb-6">

                    <label class="block text-slate-600 mb-2 font-medium">

                        Rôle

                    </label>

                    <select
                            name="role"
                            required
                            class="w-full px-4 py-3 border border-slate-300 rounded-2xl">

                        <option value="">
                            -- Sélectionner --
                        </option>

                        <option value="PCO">
                            PCO
                        </option>

                        <option value="ADMIN">
                            ADMIN
                        </option>

                        <option value="PCS">
                            PCS
                        </option>

                    </select>

                </div>

                <button
                        type="submit"
                        class="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-4 rounded-2xl">

                    Vérifier mon compte

                </button>

            </form>

            <!-- ETAPE 2 -->
            <form
                    id="step2Form"
                    action="${pageContext.request.contextPath}/admin/activer"
                    method="post"
                    class="hidden">

                <input
                        type="hidden"
                        name="email"
                        id="hiddenEmail">

                <div class="mb-5">

                    <label class="block text-slate-600 mb-2 font-medium">

                        Créer un mot de passe

                    </label>

                    <input
                            type="password"
                            name="motDePasse"
                            required
                            class="w-full px-4 py-3 border border-slate-300 rounded-2xl">

                </div>

                <div class="mb-6">

                    <label class="block text-slate-600 mb-2 font-medium">

                        Confirmer le mot de passe

                    </label>

                    <input
                            type="password"
                            name="confirmMotDePasse"
                            required
                            class="w-full px-4 py-3 border border-slate-300 rounded-2xl">

                </div>

                <button
                        type="submit"
                        class="w-full bg-emerald-600 hover:bg-emerald-700 text-white font-bold py-4 rounded-2xl">

                    Activer mon compte

                </button>

            </form>

        </div>

    </div>

</div>

<!-- SCRIPT -->
<script>

    function showTab(tab) {

        document
            .getElementById("loginForm")
            .classList.add("hidden");

        document
            .getElementById("activationForm")
            .classList.add("hidden");

        document
            .getElementById("tab1")
            .classList.remove("tab-active");

        document
            .getElementById("tab2")
            .classList.remove("tab-active");

        if (tab === 0) {

            document
                .getElementById("loginForm")
                .classList.remove("hidden");

            document
                .getElementById("tab1")
                .classList.add("tab-active");

        } else {

            document
                .getElementById("activationForm")
                .classList.remove("hidden");

            document
                .getElementById("tab2")
                .classList.add("tab-active");
        }
    }

    window.onload = function () {

        <% if (request.getAttribute("activationReady") != null) { %>

        showTab(1);

        document
            .getElementById("step1Form")
            .classList.add("hidden");

        document
            .getElementById("step2Form")
            .classList.remove("hidden");

        document
            .getElementById("hiddenEmail")
            .value = "<%= request.getAttribute("email") %>";

        <% } %>
    }

</script>

</body>
</html>
```
>>>>>>> cc8371319608e422e08ccbcba184f02a94198195
