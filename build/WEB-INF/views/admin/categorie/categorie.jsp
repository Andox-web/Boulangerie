<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.categorie.Categorie" %>
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
    <form method="POST" action="<%=request.getContextPath()%>/admin/categorie/sauvegarder">
        <input type="hidden" name="id" value="<%= request.getAttribute("categorie") != null ? ((Categorie) request.getAttribute("categorie")).getId() : "" %>">
        <input type="text" name="nom" placeholder="Nom de la catégorie" 
               value="<%= request.getAttribute("categorie") != null ? ((Categorie) request.getAttribute("categorie")).getNom() : "" %>">
        
        <textarea name="description" placeholder="Description de la catégorie">
            <%= request.getAttribute("categorie") != null ? ((Categorie) request.getAttribute("categorie")).getDescription() : "" %>
        </textarea>

        <select name="idTypeCategorie">
            <option value="">-- Sélectionnez un type de catégorie --</option>
            <% 
                List<TypeCategorie> types = (List<TypeCategorie>) request.getAttribute("typesCategorie");
                if (types != null) {
                    for (TypeCategorie type : types) {
            %>
                        <option value="<%= type.getId() %>"
                                <%= request.getAttribute("categorie") != null 
                                    && ((Categorie) request.getAttribute("categorie")).getTypeCategorie() != null 
                                    && type.getId().equals(((Categorie) request.getAttribute("categorie")).getTypeCategorie().getId()) 
                                    ? "selected" : "" %>>
                            <%= type.getNom() %>
                        </option>
            <% 
                    }
                }
            %>
        </select>
        <button type="submit">Enregistrer</button>
    </form>

    <!-- Tableau des Catégories -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Type de Catégorie</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                if (categories != null) {
                    for (Categorie categorie : categories) {
            %>
                        <tr>
                            <td><%= categorie.getId() %></td>
                            <td><%= categorie.getNom() %></td>
                            <td><%= categorie.getTypeCategorie() != null ? categorie.getTypeCategorie().getNom() : "Non défini" %></td>
                            <td><%= categorie.getDescription() != null ? categorie.getDescription() : "Non définie" %></td>
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
