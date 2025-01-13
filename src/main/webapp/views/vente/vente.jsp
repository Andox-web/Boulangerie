<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.vente.*" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Commande</title>
    <link rel="stylesheet" href="assets/css/style-commande.css">
</head>
<body>
<div class="commande">
    <h2>Détails de la vente</h2>
    <table class="commande-table">
        <thead>
            <tr>
                <th>Produit</th>
                <th>Prix Unitaire</th>
                <th>Quantité</th>
                <th>Total</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                Vente vente = (Vente) session.getAttribute("vente");
                if (vente != null && vente.getDetailVentes() != null) {
                    for (DetailVente detail : vente.getDetailVentes()) {
            %>
            <tr>
                <td><%= detail.getProduit().getNom() %></td>
                <td><%= detail.getProduit().getPrix() %> Ar</td>
                <td><%= detail.getQuantite() %></td>
                <td><%= detail.getTotal() %> Ar</td>
                <td>
                    <a href="<%=request.getContextPath()%>/ajouter-au-panier?idProduit=<%= detail.getProduit().getId() %>&quantite=<%= detail.getQuantite() + 1 %>" class="btn-action">+</a>
                    <a href="<%=request.getContextPath()%>/ajouter-au-panier?idProduit=<%= detail.getProduit().getId() %>&quantite=<%= detail.getQuantite() - 1 %>" class="btn-action">-</a>
                </td>
            </tr>
            <% 
                    }
                } else { 
            %>
            <tr>
                <td colspan="5">Votre panier est vide.</td>
            </tr>
            <% } %>
        </tbody>
    </table>
    <div class="commande-summary">
        <p><strong>Total : </strong><%= (vente != null) ? vente.getTotal() : 0 %> Ar</p>
    </div>
    
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p class="error"><%= error %></p>
    <%
        }
        String success = (String) request.getAttribute("success");
        if (success != null) {
    %>
        <p class="success"><%= success %></p>
    <%
        }
    %>
    
    <div class="commande-actions">
        <a href="<%=request.getContextPath()%>/confirm" class="btn-confirm">Confirmer la commande</a>
    </div>
</div>
        
</body>
</html>
