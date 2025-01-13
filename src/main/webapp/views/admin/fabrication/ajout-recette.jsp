<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, mg.itu.model.Produit, mg.itu.model.Ingrediant" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Ajouter une Recette</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <header>
        <h1>Ajouter une Recette</h1>
    </header>
    <main>
        <form action="<%=request.getContextPath()%>/admin/fabrication/recette/ajout" method="post">
            <label for="produit">Produit :</label>
            <select name="produit" id="produit" required>
                <%-- Boucle pour charger les produits disponibles --%>
                <% List<Produit> produits = (List<Produit>) request.getAttribute("produits"); 
                   for (Produit produit : produits) { %>
                    <option value="<%= produit.getId() %>"><%= produit.getNom() %></option>
                <% } %>
            </select>

            <label for="duree">Durée de Préparation (en minutes) :</label>
            <input type="number" name="dureePreparation" id="duree" required>

            <label for="description">Description :</label>
            <textarea name="description" id="description"></textarea>

            <fieldset>
                <legend>Détails de la Recette</legend>
                <% List<Ingrediant> ingredients = (List<Ingrediant>) request.getAttribute("ingredients"); 
                   for (Ingrediant ingredient : ingredients) { %>
                    <div>
                        <input type="checkbox" name="ingredients" value="<%= ingredient.getId() %>" id="ingredient<%= ingredient.getId() %>">
                        <label for="ingredient<%= ingredient.getId() %>"><%= ingredient.getNom() %> (<%= ingredient.getUnite() %>)</label>
                        <input type="number" name="quantite<%= ingredient.getId() %>" placeholder="Quantité">
                    </div>
                <% } %>
            </fieldset>
            <button type="submit">Ajouter</button>
        </form>
    </main>
</body>
</html>
