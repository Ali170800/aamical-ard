<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Inscription Étudiant - Amicale ARD</title>

    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f1f1f1;
            margin: 0;
            padding: 0;
        }

        .container {
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
        }

        input, select {
            width: 100%;
            padding: 12px;
            margin: 8px 0 20px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
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
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>

<div class="container">
    <h1>📝 Inscription Étudiant</h1>

    <c:if test="${not empty erreur}">
        <p class="error">${erreur}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/etudiant/inscription">

        <input type="text" name="prenom" placeholder="Prénom" required />
        <input type="text" name="nom" placeholder="Nom" required />

        <select name="sexe" required>
            <option value="">-- Sexe --</option>
            <option value="Masculin">Masculin</option>
            <option value="Féminin">Féminin</option>
        </select>

        <input type="text" name="filiere" placeholder="Filière" required />

        <!-- ✅ NIVEAU (SELECT) -->
        <select name="niveau" required>
            <option value="">-- Niveau --</option>
            <option value="L1">Licence 1</option>
            <option value="L2">Licence 2</option>
            <option value="L3">Licence 3</option>
            <option value="M1">Master 1</option>
            <option value="M2">Master 2</option>
            <option value="Doctorat">Doctorat</option>
        </select>

        <input type="text" name="anneeUniversitaire" placeholder="Année universitaire (ex: 2025-2026)" required />

        <input type="text" name="telephone" placeholder="Téléphone" required />
        <input type="text" name="numeroUrgence" placeholder="Numéro d'urgence" required />

        <!-- ✅ PATHOLOGIE (OUI/NON) -->
        <select name="pathologie" required>
            <option value="">-- Avez-vous une pathologie ? --</option>
            <option value="NON">Non</option>
            <option value="OUI">Oui</option>
        </select>

        <input type="text" name="adresse" placeholder="Adresse" required />
        <input type="email" name="email" placeholder="Email" required />

        <!-- ❌ SUPPRIMÉ : mot de passe -->

        <button type="submit">S'inscrire</button>
    </form>
</div>

</body>
</html>