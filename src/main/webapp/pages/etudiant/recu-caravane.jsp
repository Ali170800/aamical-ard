<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Reçu d'Inscription - Caravane</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f8f0; padding: 20px; }
        .recu {
            max-width: 600px;
            margin: 40px auto;
            background: white;
            padding: 40px;
            border: 3px dashed #00796b;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            text-align: center;
        }
        .numero {
            font-size: 32px;
            font-weight: bold;
            color: #d32f2f;
            margin: 20px 0;
            padding: 15px;
            background: #ffebee;
            border-radius: 10px;
        }
        .btn {
            padding: 12px 30px;
            background: #00796b;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            margin: 10px;
        }
    </style>
</head>
<body>
<div class="recu">
    <h2>✅ REÇU D'INSCRIPTION</h2>
    <p><strong>Amicale des Étudiants de Diourbel</strong></p>

    <div class="info">
        <p><strong>Nom :</strong> ${recuInscription.prenom} ${recuInscription.nom}</p>
        <p><strong>Caravane :</strong> ${caravaneRecu.nom}</p>
        <p><strong>Date :</strong> ${caravaneRecu.date}</p>
        <p><strong>Montant :</strong> ${caravaneRecu.montant} FCFA</p>
    </div>

    <div class="numero">
        Numéro de Chaise : ${recuInscription.numeroChaise}
    </div>

    <p style="color:#555; margin:20px 0;">
        Veuillez conserver ce reçu (capture d'écran ou impression).<br>
        Vous devrez présenter ce numéro le jour du départ.
    </p>

    <div>
        <a href="${pageContext.request.contextPath}/etudiant/caravanes" class="btn">Retour à mes inscriptions</a>
        <button onclick="window.print()" class="btn">🖨️ Imprimer le reçu</button>
    </div>
</div>
</body>
</html>