<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Notifications - Amicale ARD</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f9fafb; }
        .container { max-width: 900px; margin: 0 auto; background: white; padding: 25px; border-radius: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        h2 { color: #1f2937; }
        .alert { background: #fee2e2; color: #b91c1c; padding: 15px; border-radius: 8px; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 12px; border: 1px solid #e5e7eb; text-align: left; }
        th { background: #f3f4f6; }
        .no-notif { color: green; font-size: 18px; text-align: center; padding: 30px; }
    </style>
</head>
<body>
<div class="container">
    <h2>🛎️ Notifications - Paiements en retard</h2>

    <c:if test="${nombreEnRetard > 0}">
        <div class="alert">
            <strong>${nombreEnRetard} étudiants</strong> n'ont pas payé leur loyer pour le mois
            <strong>${nomMois} ${annee}</strong>.
        </div>

        <table>
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Filière</th>
                    <th>Niveau</th>
                    <th>Téléphone</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="etudiant" items="${etudiantsEnRetard}">
                <tr>
                    <td>${etudiant.nom}</td>
                    <td>${etudiant.prenom}</td>
                    <td>${etudiant.filiere}</td>
                    <td>${etudiant.niveau}</td>
                    <td><strong>${etudiant.telephone != null ? etudiant.telephone : 'Non renseigné'}</strong></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${nombreEnRetard == 0}">
        <p class="no-notif">✅ Aucun retard de paiement ce mois-ci. Tous les étudiants logés sont à jour.</p>
    </c:if>
</div>
</body>
</html>