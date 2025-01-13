<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.Produit" %>
<%@ page import="mg.itu.model.categorie.Categorie" %>
<%@ page import="mg.itu.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Produits</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Produits</h1>

    <!-- Formulaire d'Ajout/Modification -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/produit/sauvegarder">
        <input type="hidden" name="id" value="<%= request.getAttribute("produit") != null ? ((Produit) request.getAttribute("produit")).getId() : "" %>">
        <input type="text" name="nom" placeholder="Nom" value="<%= request.getAttribute("produit") != null ? ((Produit) request.getAttribute("produit")).getNom() : "" %>">
        <textarea name="description" placeholder="Description"><%= request.getAttribute("produit") != null ? ((Produit) request.getAttribute("produit")).getDescription() : "" %></textarea>
        <input type="datetime-local" name="dateCreation" value="<%= request.getAttribute("produit") != null ? DateUtil.format(((Produit) request.getAttribute("produit")).getDateCreation()) : "" %>">

        <select name="categories" multiple>
            <% 
                List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                if (categories != null) {
                    List<Categorie> selectedCategories = (List<Categorie>) (request.getAttribute("produit") != null ? ((Produit) request.getAttribute("produit")).getCategories() : null);
                    for (Categorie category : categories) {
            %>
                        <option value="<%= category.getId() %>" <%= (selectedCategories != null && selectedCategories.contains(category)) ? "selected" : "" %> >
                            <%= category.getNom() %>
                        </option>
            <% 
                    }
                }
            %>
        </select>

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

    <!-- Formulaire de Recherche -->
    <form method="GET" action="#">
        <input type="text" name="nom" placeholder="Rechercher un produit...">
        <select name="categorie">
            <option value="">Sélectionner une catégorie</option>
            <% 
                if (categories != null) {
                    for (Categorie categorie : categories) {
                    %>
                        <option value="<%= categorie.getId() %>"><%= categorie.getNom() %></option>
                    <% 
                    }
                }
            %>
        </select>
        <input type="number" name="prixMin" placeholder="Prix minimum">
        <input type="number" name="prixMax" placeholder="Prix maximum">
        <button type="submit">Rechercher</button>
    </form>
    
    <!-- Tableau des Produits -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Description</th>
                <th>Prix</th>
                <th>Quantité</th>
                <th>Date de création</th>
                <th>Catégories</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                if (produits != null) {
                    for (Produit produit : produits) {
            %>
                        <tr>
                            <td><%= produit.getId() %></td>
                            <td><%= produit.getNom() %></td>
                            <td><%= produit.getDescription() %></td>
                            <td><%= produit.getPrix() %></td>
                            <td><%=produit.getQuantite()%></td>
                            <td><%= produit.getDateCreation() %></td>
                            <td>
                                <% 
                                    if (produit.getCategories() != null) {
                                        for (Categorie cat : produit.getCategories()) {
                                %>
                                            <%= cat.getNom() %>, 
                                <% 
                                        }
                                    }
                                %>
                            </td>
                            <td>
                                <a href="?id=<%= produit.getId() %>">
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
