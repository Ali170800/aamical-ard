<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>📨 Envoyer un e‑mail aux membres du bureau</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to bottom right, #f0f4f8, #dfe9f3);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        .container {
            background: white;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 700px;
        }

        h2 {
            color: #004d99;
            text-align: center;
            margin-bottom: 20px;
        }

        form label {
            display: block;
            margin-top: 14px;
            font-weight: 600;
        }

        input[type=text],
        textarea {
            width: 100%;
            padding: 12px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 15px;
        }

        textarea {
            min-height: 150px;
        }

        .btn {
            margin-top: 20px;
            background-color: #0066cc;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
        }

        .btn:hover {
            background-color: #004080;
        }

        .info, .ok, .err {
            text-align: center;
            margin-top: 10px;
            font-size: 14px;
        }

        .ok { color: green; font-weight: bold; }
        .err { color: crimson; font-weight: bold; }

        .back-btn {
            display: flex;
            justify-content: center;
            margin-top: 30px;
        }

        .back-btn a {
            background-color: #6c757d;
            color: white;
            padding: 12px 20px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>📨 Envoyer un e‑mail aux membres du bureau</h2>

    <div class="info">📬 Destinataires détectés : <strong>${destCount}</strong></div>

    <c:if test="${not empty success}"><div class="ok">✅ ${success}</div></c:if>
    <c:if test="${not empty erreur}"><div class="err">❌ ${erreur}</div></c:if>

    <form method="post" action="${pageContext.request.contextPath}/emails/bureau">
        <label for="sujet">📝 Sujet</label>
        <input type="text" id="sujet" name="sujet" placeholder="Sujet de l’e‑mail" required />

        <label for="message">✉ Message</label>
        <textarea id="message" name="message" placeholder="Saisissez votre message..." required></textarea>

        <button type="submit" class="btn">📤 Envoyer aux membres du bureau</button>
    </form>

    <div class="back-btn">
        <!-- ✅ Bouton corrigé -->
        <a href="<%= request.getContextPath() %>/redirect-to-dashboard">
            🔙 Retour au menu
        </a>
    </div>
</div>
</body>
</html>