<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.categorie.TypeCategorie" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Catégories</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Catégories</h1>

    <!-- Formulaire d'Ajout/Modification -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/categorie/typeCategorie/sauvegarder">
        <input type="hidden" name="id" value="<%= request.getAttribute("categorie") != null ? ((TypeCategorie) request.getAttribute("categorie")).getId() : "" %>">
        <input type="text" name="nom" placeholder="Nom de la catégorie" 
               value="<%= request.getAttribute("categorie") != null ? ((TypeCategorie) request.getAttribute("categorie")).getNom() : "" %>">
        <button type="submit">Enregistrer</button>
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <% 
            }
        %>
    </form>

    <!-- Tableau des Catégories -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<TypeCategorie> categories = (List<TypeCategorie>) request.getAttribute("categories");
                if (categories != null) {
                    for (TypeCategorie categorie : categories) {
            %>
                        <tr>
                            <td><%= categorie.getId() %></td>
                            <td><%= categorie.getNom() %></td>
                            <td>
                                <a href="?id=<%= categorie.getId() %>">
                                    <button type="button" class="btn-modify">Modifier</button>
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
