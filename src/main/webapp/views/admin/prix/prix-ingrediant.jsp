<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.prix.PrixIngrediant" %>
<%@ page import="mg.itu.model.Ingrediant" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Prix Ingrediant</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Prix Ingrediant</h1>

    <!-- Filtrage par Ingrediant -->
    <form method="GET" action="<%=request.getContextPath()%>/admin/prix-ingrediant">
        <label for="Ingrediant">Ingrediant :</label>
        <select id="Ingrediant" name="idIngrediant">
            <option value="">Tous les Ingrediants</option>
            <% 
                List<Ingrediant> Ingrediants = (List<Ingrediant>) request.getAttribute("ingrediants");
                if (Ingrediants != null) {
                    for (Ingrediant Ingrediant : Ingrediants) {
            %>
                        <option value="<%= Ingrediant.getId() %>" 
                            <%= request.getParameter("idIngrediant") != null && 
                                 request.getParameter("idIngrediant").equals(Ingrediant.getId().toString()) ? "selected" : "" %>>
                            <%= Ingrediant.getNom() %>
                        </option>
            <% 
                    }
                }
            %>
        </select>
        <button type="submit">Filtrer</button>
    </form>

    <!-- Formulaire d'Ajout de Prix -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/prix-ingrediant/ajouter">
        <h2>Ajouter un Prix</h2>
        
        <label for="IngrediantAjout">Ingrediant :</label>
        <select id="IngrediantAjout" name="idIngrediant" required>
            <option value="">Sélectionnez un Ingrediant</option>
            <% 
                if (Ingrediants != null) {
                    for (Ingrediant Ingrediant : Ingrediants) {
            %>
                        <option value="<%= Ingrediant.getId() %>"><%= Ingrediant.getNom() %></option>
            <% 
                    }
                }
            %>
        </select><br>

        <label for="prix">Prix :</label>
        <input type="number" id="prix" name="prix" min="0" step="0.01" required><br>

        <button type="submit">Ajouter</button>
    </form>

    <!-- Tableau des Prix -->
    <h2>Liste des Prix</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Ingrediant</th>
                <th>Prix</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<PrixIngrediant> prixIngrediants = (List<PrixIngrediant>) request.getAttribute("prixIngrediants");
                if (prixIngrediants != null && !prixIngrediants.isEmpty()) {
                    for (PrixIngrediant prixIngrediant : prixIngrediants) {
            %>
                        <tr>
                            <td><%= prixIngrediant.getId() %></td>
                            <td><%= prixIngrediant.getIngrediant().getNom() %></td>
                            <td><%= prixIngrediant.getPrix() %></td>
                            <td><%= prixIngrediant.getDatePrix() %></td>
                        </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="4">Aucun prix trouvé.</td>
                </tr>
            <% 
                }
            %>
        </tbody>
    </table>
</body>
</html>
