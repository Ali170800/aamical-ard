<!-- /pages/fichiers/uploadFichier.jsp -->

<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter PDF</title>

    <style>

        *{
            margin:0;
            padding:0;
            box-sizing:border-box;
        }

        body{
            font-family: Arial, sans-serif;
            background:#f4f6f9;
            display:flex;
            justify-content:center;
            align-items:center;
            min-height:100vh;
            padding:20px;
        }

        .container{
            width:100%;
            max-width:550px;
            background:white;
            padding:35px;
            border-radius:15px;
            box-shadow:0 8px 25px rgba(0,0,0,0.08);
        }

        h2{
            text-align:center;
            margin-bottom:30px;
            color:#1e293b;
            font-size:28px;
        }

        form{
            width:100%;
        }

        label{
            display:block;
            margin-bottom:8px;
            font-weight:bold;
            color:#334155;
        }

        input[type="text"],
        select{
            width:100%;
            padding:12px;
            border:1px solid #cbd5e1;
            border-radius:10px;
            outline:none;
            font-size:15px;
            margin-bottom:20px;
            transition:0.3s;
        }

        input[type="text"]:focus,
        select:focus{
            border-color:#2563eb;
            box-shadow:0 0 0 3px rgba(37,99,235,0.15);
        }

        input[type="file"]{
            width:100%;
            padding:12px;
            border:2px dashed #94a3b8;
            border-radius:10px;
            background:#f8fafc;
            cursor:pointer;
            margin-bottom:25px;
        }

        input[type="file"]:hover{
            background:#eef2ff;
        }

        button{
            width:100%;
            padding:14px;
            border:none;
            border-radius:10px;
            background:#2563eb;
            color:white;
            font-size:16px;
            font-weight:bold;
            cursor:pointer;
            transition:0.3s;
        }

        button:hover{
            background:#1d4ed8;
        }

        .retour{
            display:block;
            text-align:center;
            margin-top:20px;
            text-decoration:none;
            color:#2563eb;
            font-weight:bold;
        }

        .retour:hover{
            text-decoration:underline;
        }

    </style>

</head>

<body>

<div class="container">

    <h2>Ajouter un document PDF</h2>

    <form method="post"
          enctype="multipart/form-data"
          action="${pageContext.request.contextPath}/fichiers/upload">

        <label>Titre du document</label>

        <input type="text"
               name="titre"
               placeholder="Ex: Bilan financier PCS 2025"
               required>

        <label>Type de document</label>

        <select name="typeFichier">

            <option value="Compte_Rendu">
                Compte rendu
            </option>

            <option value="Bilan_Caravane">
                Bilan caravane
            </option>

            <option value="Fiche_Activite">
                Fiche activité
            </option>

            <option value="Autre">
                Autre
            </option>

        </select>

        <label>Sélectionner un fichier PDF</label>

        <input type="file"
               name="pdf"
               accept="application/pdf"
               required>

        <button type="submit">
            Upload PDF
        </button>

    </form>

    <a class="retour"
       href="${pageContext.request.contextPath}/fichiers/liste">

        Retour à la liste des PDF

    </a>

</div>

</body>
</html>