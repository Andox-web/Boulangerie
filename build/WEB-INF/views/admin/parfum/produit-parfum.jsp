<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.Produit" %>
<%@ page import="mg.itu.model.parfum.Parfum" %>
<%@ page import="mg.itu.model.parfum.ProduitParfum" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Produits Parfums</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Produits Parfums</h1>
    <%
        ProduitParfum produitParfum = (ProduitParfum) request.getAttribute("produitParfum");
        boolean isEdit = (produitParfum != null);
    %>
    <form action="<%=request.getContextPath()%>/admin/produit-parfum/sauvegarder" method="post">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= produitParfum.getId() %>">
        <% } %>
        <label for="produit">Produit :</label>
        <select name="produit" id="produit" required>
            <% List<Produit> produits = (List<Produit>) request.getAttribute("produits"); 
               for (Produit produit : produits) { %>
                <option value="<%= produit.getId() %>" <%= isEdit && produitParfum.getProduit().getId().equals(produit.getId()) ? "selected" : "" %>><%= produit.getNom() %></option>
            <% } %>
        </select>
        
        <label for="parfum">Parfum :</label>
        <select name="parfum" id="parfum" required>
            <% List<Parfum> parfums = (List<Parfum>) request.getAttribute("parfums"); 
               for (Parfum parfum : parfums) { %>
                <option value="<%= parfum.getId() %>" <%= isEdit && produitParfum.getParfum().getId().equals(parfum.getId()) ? "selected" : "" %>><%= parfum.getNom() %></option>
            <% } %>
        </select>
        
        <button type="submit"><%= isEdit ? "Modifier" : "Ajouter" %></button>
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
    </form>

    <h2>Liste des Produits Parfums</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Produit</th>
                <th>Parfum</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% List<ProduitParfum> produitParfums = (List<ProduitParfum>) request.getAttribute("produitParfums"); 
               for (ProduitParfum pp : produitParfums) { %>
                <tr>
                    <td><%= pp.getId() %></td>
                    <td><%= pp.getProduit().getNom() %></td>
                    <td><%= pp.getParfum().getNom() %></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/admin/produit-parfum/supprimer?id=<%= pp.getId() %>">Supprimer</a>
                        <a href="<%=request.getContextPath()%>/admin/produit-parfum?id=<%= pp.getId() %>">Modifier</a>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
