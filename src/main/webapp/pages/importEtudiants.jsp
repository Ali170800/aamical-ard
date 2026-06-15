<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Importer des Étudiants</title>
    <style>
        body {
            margin: 0;
            background: #f4f7fb;
            font-family: 'Segoe UI', sans-serif;
            color: #1f2937;
        }

        .container {
            max-width: 600px;
            margin: 50px auto;
            background: #ffffff;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.08);
            padding: 30px 40px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        h2 {
            margin: 0;
            font-size: 24px;
            color: #004085;
        }

        .back-btn {
            background: #e2e8f0;
            color: #1a202c;
            padding: 10px 16px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            transition: background 0.3s ease;
        }

        .back-btn:hover {
            background: #cbd5e0;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
        }

        input[type="file"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 6px;
            margin-bottom: 20px;
            background: #f9fafb;
        }

        input[type="submit"] {
            background-color: #0066cc;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 15px;
            font-weight: bold;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #004080;
        }

        .message {
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 6px;
            font-size: 14px;
        }

        .success {
            background-color: #d1fae5;
            color: #065f46;
            border: 1px solid #10b981;
        }

        .error {
            background-color: #fee2e2;
            color: #991b1b;
            border: 1px solid #ef4444;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h2>📥 Importer des Étudiants</h2>
        <a href="${pageContext.request.contextPath}/pages/cahier.jsp" class="back-btn">🔙 Retour au menu</a>
    </div>

    <!-- Affichage du message de retour -->
    <c:if test="${not empty message}">
        <div class="message ${message.contains('succès') ? 'success' : 'error'}">
            ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/importer-etudiants" method="post" enctype="multipart/form-data">
        <label for="fichier">Sélectionner le fichier CSV :</label>
        <input type="file" name="fichier" id="fichier" accept=".csv" required>

        <input type="submit" value="📤 Importer">
    </form>
</div>

</body>
</html>