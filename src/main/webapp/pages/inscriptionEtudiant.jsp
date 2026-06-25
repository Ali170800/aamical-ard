<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">

    <!-- RESPONSIVE MOBILE -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Inscription Étudiant - Amicale ARD</title>

    <style>

        *{
            margin:0;
            padding:0;
            box-sizing:border-box;
        }

        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f1f1f1;
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            width: 100%;
            max-width: 600px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }

        h1 {
            text-align: center;
            color: #003366;
            margin-bottom: 25px;
            font-size: 2rem;
        }

        input,
        select {
            width: 100%;
            padding: 12px;
            margin: 8px 0 20px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 15px;
            outline: none;
            transition: border-color .3s ease;
        }

        input:focus,
        select:focus {
            border-color: #009688;
        }

        button {
            width: 100%;
            padding: 14px;
            background: #009688;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background .3s ease;
        }

        button:hover {
            background: #00796b;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 15px;
            font-weight: bold;
        }

        /* TABLETTE */
        @media (max-width: 768px) {

            body{
                padding:15px;
            }

            .container{
                margin:20px auto;
                padding:25px;
            }

            h1{
                font-size:1.8rem;
            }
        }

        /* MOBILE */
        @media (max-width: 480px) {

            body{
                padding:10px;
            }

            .container{
                margin:10px auto;
                padding:20px;
                border-radius:8px;
            }

            h1{
                font-size:1.5rem;
                line-height:1.3;
            }

            input,
            select{
                padding:12px;
                font-size:16px;
            }

            button{
                padding:14px;
                font-size:16px;
            }
        }

        /* TRÈS PETITS ÉCRANS */
        @media (max-width: 320px) {

            .container{
                padding:15px;
            }

            h1{
                font-size:1.3rem;
            }
        }

    </style>
</head>

<body>

<div class="container">

    <h1>📝 Inscription Étudiant</h1>

    <c:if test="${not empty erreur}">
        <p class="error">${erreur}</p>
    </c:if>

    <form method="post"
          action="${pageContext.request.contextPath}/etudiant/inscription">

        <input
                type="text"
                name="prenom"
                placeholder="Prénom"
                required />

        <input
                type="text"
                name="nom"
                placeholder="Nom"
                required />

        <select name="sexe" required>
            <option value="">-- Sexe --</option>
            <option value="Masculin">Masculin</option>
            <option value="Féminin">Féminin</option>
        </select>

        <input
                type="text"
                name="filiere"
                placeholder="Filière"
                required />

        <!-- NIVEAU -->
        <select name="niveau" required>
            <option value="">-- Niveau --</option>
            <option value="L1">Licence 1</option>
            <option value="L2">Licence 2</option>
            <option value="L3">Licence 3</option>
            <option value="M1">Master 1</option>
            <option value="M2">Master 2</option>
            <option value="Doctorat">Doctorat</option>
        </select>

        <input
                type="text"
                name="anneeUniversitaire"
                placeholder="Année universitaire (ex: 2025-2026)"
                required />

        <input
                type="text"
                name="telephone"
                placeholder="Téléphone"
                required />

        <input
                type="text"
                name="numeroUrgence"
                placeholder="Numéro d'urgence"
                required />

        <!-- PATHOLOGIE -->
        <select name="pathologie" required>
            <option value="">-- Avez-vous une pathologie ? --</option>
            <option value="NON">Non</option>
            <option value="OUI">Oui</option>
        </select>

        <input
                type="text"
                name="adresse"
                placeholder="Adresse"
                required />

        <input
                type="email"
                name="email"
                placeholder="Email"
                required />

        <!-- MOT DE PASSE SUPPRIMÉ COMME DANS LA VERSION ORIGINALE -->

        <button type="submit">
            S'inscrire
        </button>

    </form>

</div>

</body>
</html>