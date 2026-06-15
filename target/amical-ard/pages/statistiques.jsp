<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>📊 Statistiques — Amicale</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>

<style>
    :root{
        --bg:#f4f7fb; --card:#ffffff; --text:#1f2937; --muted:#6b7280;
        --blue:#3b82f6; --blue-light:#dbeafe;
        --pink:#ec4899; --pink-light:#fce7f3;
        --green:#10b981; --green-light:#d1fae5;
        --amber:#f59e0b; --amber-light:#fef3c7;
        --radius:14px; --shadow:0 10px 30px rgba(0,0,0,.06);
    }
    body{margin:0;background:var(--bg);font-family:'Segoe UI',sans-serif;color:var(--text);}
    .wrap{max-width:1200px;margin:30px auto;padding:0 16px;}
    h1{font-size:32px;margin:0 0 10px;font-weight:700}
    .subtitle{color:var(--muted);margin-bottom:30px;font-size:16px}

    /* KPIs */
    .kpis{display:grid;grid-template-columns:repeat(4,1fr);gap:18px;margin-bottom:25px}
    .kpi{background:var(--card);border-radius:var(--radius);box-shadow:var(--shadow);padding:20px;text-align:center;transition:.3s}
    .kpi:hover{transform:translateY(-3px)}
    .kpi .label{font-size:14px;color:var(--muted);margin-bottom:8px}
    .kpi .value{font-size:30px;font-weight:800;color:var(--blue)}

    .grid{display:grid;grid-template-columns:1.2fr 0.8fr;gap:20px}
    .card{background:var(--card);border-radius:var(--radius);box-shadow:var(--shadow);padding:20px}
    .card h3{margin:0 0 15px;font-size:20px;color:var(--text)}

    /* Table */
    table{width:100%;border-collapse:collapse;margin-top:10px}
    th,td{padding:12px;text-align:left;border-bottom:1px solid #e5e7eb}
    th{background:var(--blue-light);color:var(--text);font-size:15px}
    td{font-size:14px}
    .empty{color:var(--muted);padding:12px}

    @media(max-width:1000px){
        .kpis{grid-template-columns:repeat(2,1fr)}
        .grid{grid-template-columns:1fr}
    }
</style>
</head>
<body>
<div class="wrap">
<div style="text-align:right; margin-bottom: 20px;">
    <a href="${pageContext.request.contextPath}/pages/cahier.jsp"
       style="display:inline-block; background:#e5e7eb; color:#111827; padding:10px 18px; border-radius:8px;
              text-decoration:none; font-weight:600; font-size:14px; transition:background 0.3s ease;">
        🔙 Retour au menu
    </a>
</div>
    <h1>📊 Tableau de bord</h1>
    <div class="subtitle">Vue d’ensemble : effectifs, sexes, logements et filières</div>

    <!-- KPIs -->
    <div class="kpis">
        <div class="kpi"><div class="label">Étudiants (total)</div><div class="value">${totalEtudiants}</div></div>
        <div class="kpi"><div class="label">Masculin</div><div class="value">${totalGarcons}</div></div>
        <div class="kpi"><div class="label">Féminin</div><div class="value">${totalFilles}</div></div>
        <div class="kpi"><div class="label">Logés / Non logés</div><div class="value">${totalLoges} / ${totalNonLoges}</div></div>
    </div>

    <div class="grid">
        <!-- Filières -->
        <div class="card">
            <h3>📚 Répartition par filière</h3>
            <canvas id="chartFilieres" height="140"></canvas>
        </div>

        <!-- Camemberts -->
        <div class="card">
            <h3>👥 Répartition par Sexe & Logement</h3>
            <canvas id="chartSexe" height="120" style="margin-bottom:30px"></canvas>
            <canvas id="chartLogement" height="120"></canvas>
        </div>
    </div>

    <!-- Liste filières -->
    <div class="card" style="margin-top:25px;">
        <h3>Détail par filière</h3>
        <c:choose>
            <c:when test="${not empty statsParFiliere}">
                <table>
                    <thead><tr><th>Filière</th><th>Effectif</th></tr></thead>
                    <tbody>
                        <c:forEach var="entry" items="${statsParFiliere}">
                            <tr><td>${entry.key}</td><td>${entry.value}</td></tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise><div class="empty">Aucune filière enregistrée.</div></c:otherwise>
        </c:choose>
    </div>
</div>

<script>
    const totalEtudiants = Number("${totalEtudiants}");
    const totalGarcons = Number("${totalGarcons}");
    const totalFilles = Number("${totalFilles}");
    const totalLoges = Number("${totalLoges}");
    const totalNonLoges = Number("${totalNonLoges}");

    // Données filières
    const filieres = [];
    const effectifs = [];
    <c:forEach var="entry" items="${statsParFiliere}">
        filieres.push("<c:out value='${entry.key}'/>");
        effectifs.push(Number("<c:out value='${entry.value}'/>"));
    </c:forEach>

    // Barres : filières
    new Chart(document.getElementById('chartFilieres'), {
        type: 'bar',
        data: {
            labels: filieres,
            datasets: [{
                label: 'Effectif',
                data: effectifs,
                backgroundColor: '#3b82f6'
            }]
        },
        options: {
            plugins: { legend: { display: false } },
            scales: { y: { beginAtZero: true } }
        }
    });

    // Camembert Sexe
    new Chart(document.getElementById('chartSexe'), {
        type: 'doughnut',
        data: {
            labels: ['Masculin', 'Féminin'],
            datasets: [{
                data: [totalGarcons, totalFilles],
                backgroundColor: ['#60a5fa','#f472b6']
            }]
        },
        options: { plugins: { legend: { position: 'bottom' } } }
    });

    // Camembert Logement
    new Chart(document.getElementById('chartLogement'), {
        type: 'doughnut',
        data: {
            labels: ['Logés','Non logés'],
            datasets: [{
                data: [totalLoges,totalNonLoges],
                backgroundColor: ['#34d399','#fbbf24']
            }]
        },
        options: { plugins: { legend: { position: 'bottom' } } }
    });
</script>
</body>
</html>