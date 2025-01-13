<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.categorie.*" %>
<%@ page import="mg.itu.model.*" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Produits</title>
    <link rel="stylesheet" href="assets/css/style-card.css">
</head>
<body>

<header>
    <h1>Liste des Produits</h1>
</header>
<main>
    <h2>Produits Disponibles</h2>


    <form class="searchForm" action="<%=request.getContextPath()%>/produits" method="get">
        <label for="prixMin">Prix Min:</label>
        <input type="number" id="prixMin" name="prixMin" placeholder="Prix minimum">
        
        <label for="prixMax">Prix Max:</label>
        <input type="number" id="prixMax" name="prixMax" placeholder="Prix maximum">
        
        <label for="categorie">categorie</label>
        <select name="categorie" id="categorie">
            <%
                List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                for (Categorie categorie : categories) {
            %>
                <option value="<%=categorie.getId()%>"><%= categorie.getNom() %></option>
            <%
                }
            %>
        </select>
        <button type="submit">Filtrer</button>
    </form>

    <div class="product-list">
        <%
            List<Produit> produits = (List<Produit>) request.getAttribute("produits");
            for (Produit produit : produits) {
        %>
            <div class="product-item">
                <h3><a href="<%=request.getContextPath()%>/produit?id=<%=produit.getId()%>"><%= produit.getNom() %></a></h3>
                <p><%= produit.getDescription() %></p>
                <p class="price"><%= produit.getPrix() %> Ar</p>
                <div class="categories">
                    <%
                        List<Categorie> categoriesProduits = (List<Categorie>) produit.getCategories();
                        for (Categorie categorie : categoriesProduits) {
                    %>
                        <span class="category-item"><%= categorie.getNom() %></span>   
                    <%
                        }
                    %>
                </div>
            </div>
        <%
            }
        %>
    </div>
</main>

</body>
</html>
