<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.prix.PrixProduit" %>
<%@ page import="mg.itu.model.Produit" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Prix Produits</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Prix Produits</h1>

    <!-- Filtrage par Produit -->
    <form method="GET" action="<%=request.getContextPath()%>/admin/prix-produit">
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
        <label for="dateDebut">Date de début :</label>
        <input type="datetime-local" id="dateDebut" name="dateDebut" 
               value="<%= request.getParameter("dateDebut") != null ? request.getParameter("dateDebut") : "" %>"><br>

        <label for="dateFin">Date de fin :</label>
        <input type="datetime-local" id="dateFin" name="dateFin" 
               value="<%= request.getParameter("dateFin") != null ? request.getParameter("dateFin") : "" %>"><br>
        <button type="submit">Filtrer</button>
    </form>

    <!-- Formulaire d'Ajout de Prix -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/prix-produit/ajouter">
        <h2>Ajouter un Prix</h2>
        
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

        <label for="prix">Prix :</label>
        <input type="number" id="prix" name="prix" min="0" step="0.01" required><br>
        <label for="datePrix">Date du Prix :</label>
        <input type="datetime-local" id="datePrix" name="datePrixStr"><br>
        <button type="submit">Ajouter</button>
    </form>

    <!-- Tableau des Prix -->
    <h2>Liste des Prix</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Produit</th>
                <th>Prix</th>
                <th>Date</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<PrixProduit> prixProduits = (List<PrixProduit>) request.getAttribute("prixProduits");
                if (prixProduits != null && !prixProduits.isEmpty()) {
                    for (PrixProduit prixProduit : prixProduits) {
            %>
                        <tr>
                            <td><%= prixProduit.getId() %></td>
                            <td><%= prixProduit.getProduit().getNom() %></td>
                            <td><%= prixProduit.getPrix() %></td>
                            <td><%= prixProduit.getDatePrix() %></td>
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
