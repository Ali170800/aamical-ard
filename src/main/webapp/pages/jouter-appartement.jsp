<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un Appartement</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            padding: 30px;
        }

        .container {
            max-width: 600px;
            margin: auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            background-color: #28a745;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
        }

        button:hover {
            background-color: #218838;
        }

        .retour-menu {
            display: block;
            margin-top: 15px;
            padding: 12px 20px;
            background-color: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            width: 100%;
            text-align: center;
            font-size: 16px;
            box-sizing: border-box;
        }

        .retour-menu:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Ajouter un Appartement</h2>

        <form action="<%= request.getContextPath() %>/ajouterAppartement" method="post">
            <label for="nomAppartement">Nom de l'appartement</label>
            <input type="text" id="nomAppartement" name="nomAppartement" required>

            <label for="description">Adresses</label>
            <textarea id="description" name="description" rows="4"></textarea>

            <button type="submit">Ajouter</button>
        </form>

        <!-- ✅ Bouton corrigé -->
        <a href="<%= request.getContextPath() %>/redirect-to-dashboard" class="retour-menu">
            ← Retour au menu principal
        </a>
    </div>
</body>
</html>