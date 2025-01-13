<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, mg.itu.model.fabrication.Recette" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Liste des Recettes</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <header>
        <h1>Liste des Recettes</h1>
    </header>
    <main>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Produit</th>
                    <th>Durée Préparation</th>
                    <th>Date de Création</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% List<Recette> recettes = (List<Recette>) request.getAttribute("recettes"); 
                   for (Recette recette : recettes) { %>
                    <tr>
                        <td><%= recette.getId() %></td>
                        <td><%= recette.getProduit().getNom() %></td>
                        <td><%= recette.getDureePreparation() %> min</td>
                        <td><%= recette.getDateCreation() %></td>
                        <td>
                            <a href="<%=request.getContextPath()%>/admin/fabrication/recette/info?id=<%= recette.getId() %>">Détails</a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </main>
</body>
</html>
