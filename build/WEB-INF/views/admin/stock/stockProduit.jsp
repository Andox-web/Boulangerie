<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.stock.StockProduit" %>
<%@ page import="mg.itu.model.Produit" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Transactions de Stock</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Transactions de Stock</h1>

    <!-- Filtrage par Produit et Date -->
    <form method="GET" action="<%=request.getContextPath()%>/admin/stock/stock-produit">
        <label for="produit">Produit :</label>
        <select id="produit" name="idProduit">
            <option value="">Tous les produits</option>
            <% 
                List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                if (produits != null) {
                    for (Produit produit : produits) {
            %>
                        <option value="<%= produit.getId() %>" 
                            <%= request.getParameter("idProduit") != null && 
                                 request.getParameter("idProduit").equals(produit.getId().toString()) ? "selected" : "" %>>
                            <%= produit.getNom() %>
                        </option>
            <% 
                    }
                }
            %>
        </select>

        <label for="dateDebut">Date Début :</label>
        <input type="date" id="dateDebut" name="dateDebut" value="<%=request.getParameter("dateDebut")%>">

        <label for="dateFin">Date Fin :</label>
        <input type="date" id="dateFin" name="dateFin" value="<%=request.getParameter("dateFin")%>">

        <button type="submit">Filtrer</button>
    </form>

    <!-- Formulaire d'Ajout d'une Transaction -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/stock/stock-produit/ajouter">
        <h2>Ajouter une Transaction</h2>
        
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

        <label for="entree">Entrée (quantité) :</label>
        <input type="number" id="entree" name="entree" min="0" step="0.01">

        <label for="sortie">Sortie (quantité) :</label>
        <input type="number" id="sortie" name="sortie" min="0" step="0.01"><br>

        <label for="raison">Raison :</label>
        <textarea id="raison" name="raison" required></textarea><br>

        <button type="submit">Ajouter</button>
    </form>

    <!-- Tableau des Transactions -->
    <h2>Liste des Transactions</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Produit</th>
                <th>Entrée</th>
                <th>Sortie</th>
                <th>Raison</th>
                <th>Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<StockProduit> transactions = (List<StockProduit>) request.getAttribute("transactions");
                if (transactions != null && !transactions.isEmpty()) {
                    for (StockProduit transaction : transactions) {
            %>
                        <tr>
                            <td><%= transaction.getId() %></td>
                            <td><%= transaction.getProduit().getNom() %></td>
                            <td><%= transaction.getEntree() != null ? transaction.getEntree() : "-" %></td>
                            <td><%= transaction.getSortie() != null ? transaction.getSortie() : "-" %></td>
                            <td><%= transaction.getRaison() %></td>
                            <td><%= transaction.getDateTransaction() %></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/admin/stock/stock-produit/supprimer?id=<%= transaction.getId() %>">
                                    <button type="button" class="btn-delete">Supprimer</button>
                                </a>
                            </td>
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
