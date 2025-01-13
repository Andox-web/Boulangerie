<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.*" %>
<%@ page import="mg.itu.model.categorie.*" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Détails du Produit</title>
    <link rel="stylesheet" href="assets/css/style-detail.css">
</head>
<body>

<div class="content">
    
</div>
<div class="form-container">
    <main>
        <div class="product-detail">
            <%
                Produit produit = (Produit) request.getAttribute("produit");
            %>
    
            <div class="product-info">
                <h2><%= produit.getNom() %></h2>
                <p class="description"><%= produit.getDescription() %></p>
                <p class="price"><%= produit.getPrix() %> Ar</p>
    
                <div class="categories">
                    <%
                        List<Categorie> categories = (List<Categorie>) produit.getCategories();
                        for (Categorie categorie : categories) {
                    %>
                        <span class="category-item"><%= categorie.getNom() %></span>
                    <%
                        }
                    %>
                </div>
    
                <%
                    String role = (String)request.getAttribute("role");
                    if(role != null){
                        %>
                        <form action="<%=request.getContextPath()%>/ajouter-au-panier" method="get">
                            <input type="hidden" name="url" value="/produit?id=<%= produit.getId()%>">
                            <input type="hidden" name="idProduit" value="<%= produit.getId() %>">
                            <div class="quantity-control">
                                <button type="button" class="minus-btn" onclick="changeQuantity(-1)">-</button>
                                <input type="number" name="quantite" value="1" min="1" id="quantity" class="quantity-input">
                                <button type="button" class="plus-btn" onclick="changeQuantity(1)">+</button>
                            </div>
                            <button type="submit" class="add-to-cart">Ajouter au Panier</button>
                        </form>
                        <% 
                    }
                %>
            </div>
        </div>
    </main>
</div>

<script>
    function changeQuantity(change) {
        let quantityInput = document.getElementById('quantity');
        let currentQuantity = parseInt(quantityInput.value);
        let newQuantity = currentQuantity + change;
        if (newQuantity >= 1) {
            quantityInput.value = newQuantity;
        }
    }
</script>

</body>
</html>
