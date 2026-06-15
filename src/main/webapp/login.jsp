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