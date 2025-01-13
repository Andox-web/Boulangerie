<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.auth.Utilisateur" %>
<%@ page import="mg.itu.model.Produit" %>
<%@ page import="mg.itu.model.vente.Vente" %>
<%@ page import="mg.itu.model.vente.DetailVente" %>
<%@ page import="mg.itu.model.categorie.Categorie" %>
<%@ page import="mg.itu.model.parfum.Parfum" %>
<%@ page import="java.util.stream.Collectors" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ajouter Vente</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Ajouter Vente</h1>
    <form action="<%=request.getContextPath()%>/admin/vente/ajouter" method="post">
        <label for="utilisateur">Utilisateur :</label>
        <select name="utilisateur" id="utilisateur" >
            <option value="">Selectionnez un utilisateur </option>
            <% List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs"); 
               for (Utilisateur utilisateur : utilisateurs) { %>
                <option value="<%= utilisateur.getId() %>"><%= utilisateur.getNom() %></option>
            <% } %>
        </select>

        <fieldset>
            <legend>Details de la Vente</legend>
            <% List<Produit> produits = (List<Produit>) request.getAttribute("produits"); 
               for (Produit produit : produits) { %>
                <div>
                    <input type="checkbox" name="produits" value="<%= produit.getId() %>" id="produit<%= produit.getId() %>">
                    <label for="produit<%= produit.getId() %>"><%= produit.getNom() %> (<%= produit.getPrix() %>)</label>
                    <input type="number" name="quantite<%= produit.getId() %>" placeholder="Quantite">
                </div>
            <% } %>
        </fieldset>
        
        <button type="submit">Ajouter</button>
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

    <h2>Filtrer les Produits</h2>
    <form action="<%=request.getContextPath()%>/admin/vente" method="get">
        <label for="categorie">Categorie :</label>
        <select name="categorie" id="categorie">
            <option value="">Selectionnez une categorie</option>
            <% Long selectedCategorie = (Long) request.getAttribute("categorie"); %>
            <% List<Categorie> categories = (List<Categorie>) request.getAttribute("categories"); 
               for (Categorie categorie : categories) { %>
                <option value="<%= categorie.getId() %>" <%= (selectedCategorie != null && selectedCategorie.equals(categorie.getId())) ? "selected" : "" %>><%= categorie.getNom() %></option>
            <% } %>
        </select>

        <label for="parfum">Parfum :</label>
        <select name="parfum" id="parfum">
            <option value="">Selectionnez un parfum</option>
            <option value="0" <%= (request.getAttribute("parfum") != null && request.getAttribute("parfum").equals(0L)) ? "selected" : "" %>>Nature</option>
            <% Long selectedParfum = (Long) request.getAttribute("parfum"); %>
            <% List<Parfum> parfums = (List<Parfum>) request.getAttribute("parfums"); 
               for (Parfum parfum : parfums) { %>
                <option value="<%= parfum.getId() %>" <%= (selectedParfum != null && selectedParfum.equals(parfum.getId())) ? "selected" : "" %>><%= parfum.getNom() %></option>
            <% } %>
        </select>

        <button type="submit">Filtrer</button>
    </form>

    <h2>Liste des Ventes</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Utilisateur</th>
                <th>Total</th>
                <th>Date de Vente</th>
                <th>Details de la Vente</th>
            </tr>
        </thead>
        <tbody>
            <% List<Vente> ventes = (List<Vente>) request.getAttribute("ventes"); 
               for (Vente vente : ventes) { %>
                <tr>
                    <td><%= vente.getId() %></td>
                    <td><%= vente.getUtilisateur()!=null?vente.getUtilisateur().getNom():" " %></td>
                    <td><%= vente.getTotal() %> </td>
                    <td><%= vente.getDateVente() %></td>
                    <td>
                        <table>
                            <thead>
                                <tr>
                                    <th>Produit</th>
                                    <th>Quantite</th>
                                    <th>Prix Unitaire</th>
                                    <th>Total</th>
                                    <th>Categories</th>
                                    <th>Parfums</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (DetailVente detail : vente.getDetailVentes()) { %>
                                    <tr>
                                        <td><%= detail.getProduit().getNom() %></td>
                                        <td><%= detail.getQuantite() %></td>
                                        <td><%= detail.getPrixUnitaire() %></td>
                                        <td><%= detail.getTotal() %></td>
                                        <td><%= detail.getProduit().getProduitCategories().isEmpty() ? "Aucun" : detail.getProduit().getProduitCategories().stream()
                                            .map(pc -> pc.getCategorie().getNom())
                                            .collect(Collectors.joining(", ")) %></td>
                                        <td><%= detail.getProduit().getProduitParfums().isEmpty() ? "Nature" : detail.getProduit().getProduitParfums().stream()
                                            .map(pp -> pp.getParfum().getNom())
                                            .collect(Collectors.joining(", ")) %></td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>