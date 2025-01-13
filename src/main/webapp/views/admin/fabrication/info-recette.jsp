<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mg.itu.model.fabrication.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Informations sur la Recette</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-info.css">
</head>
<body>
    <header>
        <h1>Informations sur la Recette</h1>
    </header>
    <main>
        <% Recette recette = (Recette) request.getAttribute("recette"); %>
        <h2>Produit : <%= recette.getProduit().getNom() %></h2>
        <p>Durée de Préparation : <%= recette.getDureePreparation() %> seconde</p>
        <p>Description : <%= recette.getDescription() != null ? recette.getDescription() : "Aucune description." %></p>
        <p>Date de Création : <%= recette.getDateCreation() %></p>

        <h3>Détails des Ingrédients :</h3>
        <ul>
            <% for (DetailRecette detail : recette.getDetailRecettes()) { %>
                <li><%= detail.getIngrediant().getNom() %> : <%= detail.getQuantite() %> <%= detail.getIngrediant().getUnite() %></li>
            <% } %>
        </ul>

        <a href="<%=request.getContextPath()%>/admin/fabrication/recette/liste">Retour à la liste</a>
    </main>
</body>
</html>
