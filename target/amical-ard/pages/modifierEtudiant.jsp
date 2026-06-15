<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier Étudiant</title>
    <!-- Balises anti-cache -->
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    
    <style>
        :root {
            --primary: #3498db;
            --secondary: #2c3e50;
            --success: #2ecc71;
            --danger: #e74c3c;
            --light-bg: #f5f7fa;
            --form-bg: #ffffff;
            --border: #e0e6ed;
            --shadow: rgba(0, 0, 0, 0.08);
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background-color: var(--light-bg);
            color: #333;
            line-height: 1.6;
            padding: 20px;
        }
        
        .container {
            max-width: 900px;
            margin: 30px auto;
            background: var(--form-bg);
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 10px 30px var(--shadow);
        }
        
        header {
            background: linear-gradient(135deg, var(--secondary), var(--primary));
            color: white;
            padding: 25px 30px;
            position: relative;
        }
        
        header h1 {
            font-size: 28px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 12px;
        }
        
        header h1 i {
            font-size: 26px;
        }
        
        .form-body {
            padding: 30px;
        }
        
        .error-notification {
            background: #ffebee;
            color: #c62828;
            padding: 15px 20px;
            border-radius: 8px;
            border-left: 4px solid #c62828;
            margin-bottom: 25px;
            display: flex;
            align-items: center;
            gap: 12px;
            font-weight: 500;
        }
        
        .error-notification i {
            font-size: 20px;
        }
        
        .form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 25px;
            margin-bottom: 30px;
        }
        
        .form-section {
            margin-bottom: 35px;
        }
        
        .section-title {
            font-size: 18px;
            color: var(--secondary);
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid var(--primary);
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .section-title i {
            color: var(--primary);
        }
        
        .form-group {
            margin-bottom: 22px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #555;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        
        .form-group label i {
            color: var(--primary);
            font-size: 18px;
        }
        
        .form-control {
            width: 100%;
            padding: 14px 16px;
            border: 1px solid var(--border);
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s ease;
            background: #fafbfc;
        }
        
        .form-control:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.2);
            outline: none;
            background: white;
        }
        
        .full-width {
            grid-column: 1 / -1;
        }
        
        .form-actions {
            display: flex;
            justify-content: flex-end;
            gap: 15px;
            padding-top: 20px;
            border-top: 1px solid var(--border);
            margin-top: 10px;
        }
        
        .btn {
            padding: 14px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        
        .btn-primary {
            background: linear-gradient(to right, var(--primary), #2980b9);
            color: white;
        }
        
        .btn-primary:hover {
            background: linear-gradient(to right, #2980b9, var(--primary));
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(52, 152, 219, 0.3);
        }
        
        .btn-danger {
            background: linear-gradient(to right, var(--danger), #c0392b);
            color: white;
        }
        
        .btn-danger:hover {
            background: linear-gradient(to right, #c0392b, var(--danger));
            transform: translateY(-2px);
            box-shadow: 0 6px 12px rgba(231, 76, 60, 0.3);
        }
        
        .student-id {
            position: absolute;
            right: 30px;
            top: 30px;
            background: rgba(255, 255, 255, 0.15);
            padding: 8px 15px;
            border-radius: 20px;
            font-size: 15px;
            font-weight: 500;
            backdrop-filter: blur(10px);
        }
        
        @media (max-width: 768px) {
            .form-grid {
                grid-template-columns: 1fr;
            }
            
            header {
                text-align: center;
                flex-direction: column;
            }
            
            .student-id {
                position: static;
                margin-top: 15px;
                display: inline-block;
            }
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>
                <i class="fas fa-user-edit"></i>
                Modifier les informations de l'étudiant
            </h1>
            <div class="student-id">
                <i class="fas fa-id-card"></i>
                ID: ${etudiant.id}
            </div>
        </header>
        
        <div class="form-body">
            <c:if test="${not empty error}">
                <div class="error-notification">
                    <i class="fas fa-exclamation-circle"></i>
                    ${error}
                </div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/mettreAJourEtudiant" method="POST">
                <input type="hidden" name="id" value="${etudiant.id}">
                
                <div class="form-section">
                    <h2 class="section-title">
                        <i class="fas fa-user"></i>
                        Informations personnelles
                    </h2>
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="nom">
                                <i class="fas fa-signature"></i>
                                Nom
                            </label>
                            <input type="text" id="nom" name="nom" class="form-control" 
                                   value="${etudiant.nom}" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="prenom">
                                <i class="fas fa-signature"></i>
                                Prénom
                            </label>
                            <input type="text" id="prenom" name="prenom" class="form-control" 
                                   value="${etudiant.prenom}" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="sexe">
                                <i class="fas fa-venus-mars"></i>
                                Sexe
                            </label>
                            <select id="sexe" name="sexe" class="form-control" required>
                                <option value="Masculin" ${etudiant.sexe == 'Masculin' ? 'selected' : ''}>Masculin</option>
                                <option value="Féminin" ${etudiant.sexe == 'Féminin' ? 'selected' : ''}>Féminin</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="email">
                                <i class="fas fa-envelope"></i>
                                Email
                            </label>
                            <input type="email" id="email" name="email" class="form-control" 
                                   value="${etudiant.email}">
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="adresse">
                                <i class="fas fa-map-marker-alt"></i>
                                Adresse
                            </label>
                            <input type="text" id="adresse" name="adresse" class="form-control" 
                                   value="${etudiant.adresse}">
                        </div>
                    </div>
                </div>
                
                <div class="form-section">
                    <h2 class="section-title">
                        <i class="fas fa-graduation-cap"></i>
                        Informations académiques
                    </h2>
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="filiere">
                                <i class="fas fa-book"></i>
                                Filière
                            </label>
                            <input type="text" id="filiere" name="filiere" class="form-control" 
                                   value="${etudiant.filiere}">
                        </div>
                        
                        <div class="form-group">
                            <label for="niveau">
                                <i class="fas fa-layer-group"></i>
                                Niveau
                            </label>
                            <input type="text" id="niveau" name="niveau" class="form-control" 
                                   value="${etudiant.niveau}">
                        </div>
                        
                        <div class="form-group">
                            <label for="anneeUniversitaire">
                                <i class="fas fa-calendar-alt"></i>
                                Année Universitaire
                            </label>
                            <input type="text" id="anneeUniversitaire" name="anneeUniversitaire" class="form-control" 
                                   value="${etudiant.anneeUniversitaire}">
                        </div>
                    </div>
                </div>
                
                <div class="form-section">
                    <h2 class="section-title">
                        <i class="fas fa-heartbeat"></i>
                        Informations de santé
                    </h2>
                    
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="telephone">
                                <i class="fas fa-phone"></i>
                                Téléphone
                            </label>
                            <input type="text" id="telephone" name="telephone" class="form-control" 
                                   value="${etudiant.telephone}">
                        </div>
                        
                        <div class="form-group">
                            <label for="numeroUrgence">
                                <i class="fas fa-phone-alt"></i>
                                Numéro d'urgence
                            </label>
                            <input type="text" id="numeroUrgence" name="numeroUrgence" class="form-control" 
                                   value="${etudiant.numeroUrgence}">
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="pathologie">
                                <i class="fas fa-file-medical"></i>
                                Pathologie
                            </label>
                            <input type="text" id="pathologie" name="pathologie" class="form-control" 
                                   value="${etudiant.pathologie}">
                        </div>
                    </div>
                </div>
                
                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/etudiants" class="btn btn-danger">
                        <i class="fas fa-times"></i>
                        Annuler
                    </a>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i>
                        Mettre à jour
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>