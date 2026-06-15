<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>➕ Ajouter une Activité</title>
    <style>
        body {
            margin: 0;
            background: #f4f7fb;
            font-family: 'Segoe UI', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background: white;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 600px;
        }

        h2 {
            color: #004085;
            text-align: center;
            margin-bottom: 25px;
        }

        label {
            font-weight: 600;
            display: block;
            margin-top: 15px;
        }

        input[type="text"],
        input[type="date"] {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 15px;
        }

        input[type="submit"] {
            background-color: #28a745;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
            font-weight: bold;
            margin-top: 25px;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #218838;
        }

        .buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 25px;
            flex-wrap: wrap;
        }

        .btn-link {
            background: #e2e8f0;
            color: #1a202c;
            padding: 10px 16px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            transition: background 0.3s ease;
            margin-top: 10px;
        }

        .btn-link:hover {
            background: #cbd5e0;
        }

        .center-links {
            text-align: center;
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>📌 Ajouter une Nouvelle Activité</h2>

    <form action="ajouterActivite" method="post">
        <label for="nom">📝 Nom de l'activité :</label>
        <input type="text" name="nom" required>

        <label for="dateActivite">📅 Date de l'activité :</label>
        <input type="date" name="dateActivite" required>

        <label for="lieu">📍 Lieu :</label>
        <input type="text" name="lieu" required>

        <input type="submit" value="➕ Ajouter l'activité">
    </form>

    <div class="buttons">
        <a class="btn-link" href="listeActivites">📂 Voir les Activités</a>
       <div class="center-links">
        <a class="btn-link" href="pages/cahier.jsp">🔙 Retour au menu</a>
    </div> 
    </div>

   
</div>
</body>
</html>