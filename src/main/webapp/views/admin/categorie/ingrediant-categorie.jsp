<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.categorie.Categorie" %>
<%@ page import="mg.itu.model.Ingrediant" %>
<%@ page import="mg.itu.model.categorie.IngrediantCategorie" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Ingrédients par Catégorie</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Ingrédients par Catégorie</h1>

    <!-- Formulaire d'Ajout -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/ingrediant-categorie/sauvegarder">
        <select name="idIngrediant" required>
            <option value="">-- Sélectionnez un ingrédient --</option>
            <% 
                List<Ingrediant> ingrediants = (List<Ingrediant>) request.getAttribute("ingrediants");
                if (ingrediants != null) {
                    for (Ingrediant ingrediant : ingrediants) {
            %>
                        <option value="<%= ingrediant.getId() %>"><%= ingrediant.getNom() %></option>
            <% 
                    }
                }
            %>
        </select>

        <select name="idCategorie" required>
            <option value="">-- Sélectionnez une catégorie --</option>
            <% 
                List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                if (categories != null) {
                    for (Categorie categorie : categories) {
            %>
                        <option value="<%= categorie.getId() %>"><%= categorie.getNom() %></option>
            <% 
                    }
                }
            %>
        </select>

        <button type="submit">Ajouter</button>
    </form>

    <!-- Tableau des Associations -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Ingrédient</th>
                <th>Catégorie</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<IngrediantCategorie> ingrediantCategories = (List<IngrediantCategorie>) request.getAttribute("ingrediantCategories");
                if (ingrediantCategories != null) {
                    for (IngrediantCategorie ingrediantCategorie : ingrediantCategories) {
            %>
                        <tr>
                            <td><%= ingrediantCategorie.getId() %></td>
                            <td><%= ingrediantCategorie.getIngrediant().getNom() %></td>
                            <td><%= ingrediantCategorie.getCategorie().getNom() %></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/admin/ingrediant-categorie/supprimer?id=<%= ingrediantCategorie.getId() %>">
                                    <button type="button" class="btn-delete">Supprimer</button>
                                </a>
                            </td>
                        </tr>
            <% 
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>
