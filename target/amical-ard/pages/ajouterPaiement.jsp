<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.amical.ard.entites.LogementEtudiant" %>
<%@ page import="com.amical.ard.entites.Etudiant" %>

<%
    List<LogementEtudiant> logements =
            (List<LogementEtudiant>) request.getAttribute("logements");
%>

<html>
<head>
    <title>💸 Ajouter un Paiement</title>

    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #f4f7f9;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 900px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #006699;
            margin-bottom: 25px;
        }

        .search-box {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 6px;
            font-size: 16px;
        }

        .students-list {
            max-height: 450px;
            overflow-y: auto;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 10px;
            background: #fafafa;
        }

        .student-item {
            padding: 12px;
            margin: 6px 0;
            background: white;
            border-radius: 6px;
            border: 1px solid #eee;
        }

        select,
        input[type="number"] {
            width: 100%;
            padding: 12px;
            margin: 10px 0 20px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        input[type="submit"] {
            width: 100%;
            padding: 14px;
            background-color: #006699;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #004d66;
        }

        .top-bar {
            margin-bottom: 20px;
            text-align: right;
        }

        /* ========================= */
        /* AJOUT MESSAGE ERREUR/SUCCÈS */
        /* ========================= */

        .message-error {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
            line-height: 1.7;
        }

        .message-success {
            background: #d4edda;
            color: #155724;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border: 1px solid #c3e6cb;
            line-height: 1.7;
        }
    </style>
</head>

<body>

<div class="top-bar">
    <a href="<%= request.getContextPath() %>/listePaiements"
       style="padding:10px 20px; background:#006699; color:white; text-decoration:none; border-radius:6px;">
        Voir les paiements
    </a>
</div>

<div class="container">

    <h2>💸 Ajouter un Paiement</h2>

    <!-- ========================= -->
    <!-- MESSAGE D'ERREUR -->
    <!-- ========================= -->

    <%
        String error =
                (String) session.getAttribute("error");

        if (error != null) {
    %>

        <div class="message-error">
            <%= error %>
        </div>

    <%
            session.removeAttribute("error");
        }
    %>

    <!-- ========================= -->
    <!-- MESSAGE SUCCÈS -->
    <!-- ========================= -->

    <%
        String success =
                (String) session.getAttribute("success");

        if (success != null) {
    %>

        <div class="message-success">
            <%= success %>
        </div>

    <%
            session.removeAttribute("success");
        }
    %>

    <form action="<%= request.getContextPath() %>/ajouterPaiement"
          method="post">

        <!-- Recherche dynamique -->
        <input
                type="text"
                id="searchInput"
                class="search-box"
                placeholder="Rechercher par nom, prénom, téléphone..."
                onkeyup="filterStudents()"
        >

        <!-- Liste des étudiants -->
        <label>
            <strong>Sélectionnez les étudiants à payer :</strong>
        </label>

        <div class="students-list" id="studentsList">

            <%
                if (logements != null) {

                    for (LogementEtudiant l : logements) {

                        Etudiant e = l.getEtudiant();

                        if (e == null) {
                            continue;
                        }
            %>

            <div class="student-item">

                <label>

                    <input
                            type="checkbox"
                            name="etudiantIds"
                            value="<%= e.getId() %>"
                    >

                    <strong>
                        <%= e.getPrenom() %>
                        <%= e.getNom() %>
                    </strong>

                    -
                    <%= e.getTelephone() != null
                            ? e.getTelephone()
                            : "" %>

                    <small
                            style="margin-left:auto;color:#666;"
                    >
                        <%= l.getAppartement() != null
                                ? l.getAppartement().getNomAppartement()
                                : "" %>
                    </small>

                </label>

            </div>

            <%
                    }
                }
            %>

        </div>

        <!-- MOIS -->

        <label for="mois">📅 Mois :</label>

        <select name="mois" required>

            <option value="">
                -- Sélectionner le mois --
            </option>

            <% for (int i = 1; i <= 12; i++) { %>

                <option value="<%= i %>">
                    <%= i %>
                </option>

            <% } %>

        </select>

        <!-- ANNÉE -->

        <label for="annee">📆 Année :</label>

        <input
                type="number"
                name="annee"
                placeholder="ex: 2026"
                required
        />

        <!-- MONTANT -->

        <label for="montant">
            💵 Montant (FCFA) :
        </label>

        <input
                type="number"
                name="montant"
                step="0.01"
                required
        />

        <!-- STATUT -->

        <label for="statut">
            ✅ Statut du paiement :
        </label>

        <select name="statut" required>

            <option value="">
                -- Sélectionner --
            </option>

            <option value="PAYE">
                Payé
            </option>

            <option value="IMPAYE">
                Impayé
            </option>

        </select>

        <input
                type="submit"
                value="💾 Enregistrer le(s) paiement(s)"
        />

    </form>

</div>

<script>

function filterStudents() {

    const input =
        document.getElementById("searchInput")
            .value
            .toLowerCase();

    const items =
        document.querySelectorAll(".student-item");

    items.forEach(item => {

        const text =
            item.textContent.toLowerCase();

        item.style.display =
            text.includes(input)
                ? "block"
                : "none";
    });
}

</script>

</body>
</html>