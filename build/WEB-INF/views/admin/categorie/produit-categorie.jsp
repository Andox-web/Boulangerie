<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.categorie.Categorie" %>
<%@ page import="mg.itu.model.Produit" %>
<%@ page import="mg.itu.model.categorie.ProduitCategorie" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Produits par Catégorie</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Produits par Catégorie</h1>

    <!-- Formulaire d'Ajout -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/categorie/produit-categorie/sauvegarder">
        <select name="idProduit" required>
            <option value="">-- Sélectionnez un produit --</option>
            <% 
                List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                if (produits != null) {
                    for (Produit produit : produits) {
            %>
                        <option value="<%= produit.getId() %>"><%= produit.getNom() %></option>
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
                <th>Produit</th>
                <th>Catégorie</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<ProduitCategorie> produitCategories = (List<ProduitCategorie>) request.getAttribute("produitCategories");
                if (produitCategories != null) {
                    for (ProduitCategorie produitCategorie : produitCategories) {
            %>
                        <tr>
                            <td><%= produitCategorie.getId() %></td>
                            <td><%= produitCategorie.getProduit().getNom() %></td>
                            <td><%= produitCategorie.getCategorie().getNom() %></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/admin/categorie/produit-categorie/supprimer?id=<%= produitCategorie.getId() %>">
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
