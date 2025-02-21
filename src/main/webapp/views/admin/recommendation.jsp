<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.*" %>
<%@ page import="mg.itu.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Utilisateurs</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Recommendation</h1>

    <h3>Insertion Recommendation</h3>
    <!-- Formulaire d'Ajout/Modification -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/recommendation/insert">
        
        <!-- Sélection des rôles -->
        <select name="idProduit">
            <option value="">Selectionnez un produit</option>
            <% 
                List<Produit> produits = (List<Produit>) request.getAttribute("produits");
                if (produits != null) {
                    for (Produit produit : produits) {
            %>
                        <option value="<%= produit.getId() %>">
                            <%= produit.getNom() %>
                        </option>
            <% 
                    }
                }
            %>
        </select>
        <input type="datetime-local" name="debutStr" placeholder="Date Debut">
        <input type="datetime-local" name="finStr" placeholder="Date Fin">
        <button type="submit">Enregistrer</button>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <%
            }
        %>
        <%
            String success = (String) request.getAttribute("success");
            if (success != null) {
        %>
            <p class="success"><%= success %></p>
        <%
            }
        %>
    </form>

    <!-- Formulaire de Recherche -->
    <h3>Recherche par dates</h3>
    <form method="GET" action="#">
        <label for="annee">annee</label>
        <input type="number" name="annee" placeholder="annee">
        <input type="datetime-local" name="debutStr" placeholder="Date Debut" >
        <input type="datetime-local" name="finStr" placeholder="Date Fin" >
        <button type="submit">Rechercher</button>
    </form>
    
    <!-- Tableau des Utilisateurs -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Date de Debut</th>
                <th>Date de Fin</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Recommendation> recommendations = (List<Recommendation>) request.getAttribute("recommendations");
                if (recommendations != null) {
                    for (Recommendation recommendation : recommendations) {
            %>
                        <tr>
                            <td><%= recommendation.getId() %></td>
                            <td><%= recommendation.getProduit().getNom() %></td>
                            <td><%= DateUtil.format(recommendation.getDateDebut()) %></td>
                            <td><%= DateUtil.format(recommendation.getDateFin()) %></td>
                        </tr>
            <% 
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>
