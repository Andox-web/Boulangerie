<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.stock.StockIngrediant" %>
<%@ page import="mg.itu.model.Ingrediant" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Transactions de Stock d'Ingrédients</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Transactions de Stock d'Ingrédients</h1>

    <!-- Filtrage par Ingrédient et Date -->
    <form method="GET" action="<%=request.getContextPath()%>/admin/stock/stock-ingrediant">
        <label for="ingrediant">Ingrédient :</label>
        <select id="ingrediant" name="idIngrediant">
            <option value="">Tous les ingrédients</option>
            <% 
                List<Ingrediant> ingredients = (List<Ingrediant>) request.getAttribute("ingredients");
                if (ingredients != null) {
                    for (Ingrediant ingrediant : ingredients) {
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

        <label for="dateDebut">Date Début :</label>
        <input type="date" id="dateDebut" name="dateDebut" value="<%=request.getParameter("dateDebut")%>">

        <label for="dateFin">Date Fin :</label>
        <input type="date" id="dateFin" name="dateFin" value="<%=request.getParameter("dateFin")%>">

        <button type="submit">Filtrer</button>
    </form>

    <!-- Formulaire d'Ajout d'une Transaction -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/stock/stock-ingrediant/ajouter">
        <h2>Ajouter une Transaction</h2>
        
        <label for="ingrediantAjout">Ingrédient :</label>
        <select id="ingrediantAjout" name="idIngrediant" required>
            <option value="">Sélectionnez un ingrédient</option>
            <% 
                if (ingredients != null) {
                    for (Ingrediant ingrediant : ingredients) {
            %>
                        <option value="<%= ingrediant.getId() %>"><%= ingrediant.getNom() %></option>
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
                <th>Ingrédient</th>
                <th>Entrée</th>
                <th>Sortie</th>
                <th>Raison</th>
                <th>Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<StockIngrediant> transactions = (List<StockIngrediant>) request.getAttribute("transactions");
                if (transactions != null && !transactions.isEmpty()) {
                    for (StockIngrediant transaction : transactions) {
            %>
                        <tr>
                            <td><%= transaction.getId() %></td>
                            <td><%= transaction.getIngrediant().getNom() %></td>
                            <td><%= transaction.getEntree() != null ? transaction.getEntree() : "-" %></td>
                            <td><%= transaction.getSortie() != null ? transaction.getSortie() : "-" %></td>
                            <td><%= transaction.getRaison() %></td>
                            <td><%= transaction.getDateTransaction() %></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/admin/stock/stock-ingrediant/supprimer?id=<%= transaction.getId() %>">
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
