<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Créer une Caravane</title>

    <style>
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f7f9fc;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 500px;
            margin: 50px auto;
            background-color: #ffffff;
            padding: 30px 40px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #007bff;
            margin-bottom: 25px;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
            color: #333;
        }

        input[type="text"],
        input[type="date"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }

        button[type="submit"] {
            background-color: #28a745;
            color: white;
            padding: 12px;
            margin-top: 25px;
            width: 100%;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }

        button[type="submit"]:hover {
            background-color: #218838;
        }

        .actions {
            display: flex;
            justify-content: space-between;
            margin: 20px auto;
            max-width: 500px;
        }

        .actions a {
            text-decoration: none;
            padding: 10px 18px;
            border-radius: 4px;
            font-size: 14px;
            color: white;
        }

        .btn-primary {
            background-color: #007bff;
        }

        .btn-primary:hover {
            background-color: #0069d9;
        }

        .btn-secondary {
            background-color: #6c757d;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
        }
    </style>
</head>

<body>

<!-- ACTIONS -->
<div class="actions">
    <a href="<%= request.getContextPath() %>/caravane/lister" class="btn-primary">
        📄 Voir la liste des caravanes
    </a>

    <!-- 🔥 CORRECTION ICI -->
    <a href="<%= request.getContextPath() %>/redirect-to-dashboard" class="btn-secondary">
        ↩ Retour au menu
    </a>
</div>

<!-- FORMULAIRE -->
<div class="container">
    <h2>➕ Créer une nouvelle Caravane</h2>

    <form action="<%= request.getContextPath() %>/caravane/creer" method="post">

        <label for="nom">Nom de la caravane :</label>
        <input type="text" id="nom" name="nom" required>

        <label for="date">Date de la caravane :</label>
        <input type="date" id="date" name="date" required>

        <label for="montant">Montant fixé (FCFA) :</label>
        <input type="number" id="montant" name="montant" required min="0">

        <button type="submit">Créer</button>
    </form>
</div>

</body>
</html>