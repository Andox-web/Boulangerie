<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.fabrication.*" %>
<%@ page import="mg.itu.model.Produit" %>
<%@ page import="mg.itu.model.Ingrediant" %>
<%@ page import="mg.itu.model.categorie.Categorie" %>
<%
    List<Produit> produits = (List<Produit>) request.getAttribute("produits");            
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Fabrication</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Fabrication</h1>

    <!-- Filtrage par Produit et Date -->
    <form method="GET" action="<%=request.getContextPath()%>/admin/fabrication/fabrication">
        <label for="Categorie">Categorie :</label>
        <select id="Categorie" name="idCategorie">
            <option value="">Tous les Categories</option>
            <% 
                List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");            
                if (categories != null) {
                    for (Categorie categorie : categories) {
            %>
                        <option value="<%= categorie.getId() %>" 
                            <%= request.getParameter("idCategorie") != null && 
                                 request.getParameter("idCategorie").equals(categorie.getId().toString()) ? "selected" : "" %>>
                            <%= categorie.getNom() %>
                        </option>
            <% 
                    }
                }
            %>
        </select>
        <select id="ingrediant" name="idIngrediant">
            <option value="">Tous les ingrediants</option>
            <% 
                List<Ingrediant> ingrediants = (List<Ingrediant>) request.getAttribute("ingrediants");
                if (ingrediants != null) {
                    for (Ingrediant ingrediant : ingrediants) {
            %>
                        <option value="<%= ingrediant.getId() %>" 
                            <%= request.getParameter("idIngrediant") != null && 
                                 request.getParameter("idIngrediant").equals(ingrediant.getId().toString()) ? "selected" : "" %>>
                            <%= ingrediant.getNom() %>
                        </option>
            <% 
                    }
                }
            %>
        </select>
        <button type="submit">Filtrer</button>
    </form>

    <!-- Formulaire d'Ajout d'une Transaction -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/fabrication/fabrication/ajouter">
        <h2>Ajouter une Fabrication</h2>
        
        <label for="produitAjout">Produit :</label>
        <select id="produitAjout" name="idProduit" required>
            <option value="">Sélectionnez un produit</option>
            <% 
                if (produits != null) {
                    for (Produit produit : produits) {
            %>
                        <option value="<%= produit.getId() %>"><%= produit.getNom() %></option>
            <% 
                    }
                }
            %>
        </select><br>

        <label for="quantite">quantité :</label>
        <input type="number" id="quantite" name="quantite" min="0" >

        <button type="submit">Ajouter</button>
        <%
        String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <%
            }
        %>
    </form>

    <!-- Tableau des Transactions -->
    <h2>Liste des Transactions</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Produit</th>
                <th>Ingrediant</th>
                <th>Quantite</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Fabrication> transactions = (List<Fabrication>) request.getAttribute("transactions");
                if (transactions != null && !transactions.isEmpty()) {
                    for (Fabrication transaction : transactions) {
            %>
                        <tr>
                            <td><%= transaction.getId() %></td>
                            <td><%= transaction.getProduit().getNom() %></td>
                            <td>
                                <%
                                    for (DetailRecette detail : transaction.getRecette().getDetailRecettes()) {
                                        %>
                                        <p><%=detail.getIngrediant().getNom()%></p>
                                        <%
                                    }
                                %>
                            </td>
                            <td><%=transaction.getQuantite()%></td>
                        </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="7">Aucune transaction trouvée.</td>
                </tr>
            <% 
                }
            %>
        </tbody>
    </table>
</body>
</html>
