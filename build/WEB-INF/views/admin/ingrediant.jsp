<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.Ingrediant" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Ingrédients</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Ingrédients</h1>

    <!-- Formulaire de Recherche -->
    <form method="GET" action="<%=request.getContextPath()%>/admin/ingrediant/recherche">
        <label for="recherche">Recherche:</label>
        <input type="text" id="recherche" name="recherche" placeholder="Nom ou description" value="<%=request.getParameter("recherche")%>">
        <button type="submit">Rechercher</button>
    </form>

    <!-- Formulaire d'Ajout ou de Modification d'Ingrédient -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/ingrediant/ajouter">
        <h2><%=request.getAttribute("mode") == null ? "Ajouter un Ingrédient" : "Modifier un Ingrédient" %></h2>
        <input type="hidden" name="id" value="<%=request.getAttribute("ingrediant") != null ? ((Ingrediant) request.getAttribute("ingrediant")).getId() : ""%>">
        
        <label for="nom">Nom de l'Ingrédient:</label>
        <input type="text" id="nom" name="nom" required value="<%=request.getAttribute("ingrediant") != null ? ((Ingrediant) request.getAttribute("ingrediant")).getNom() : ""%>"><br>

        <label for="unite">Unité:</label>
        <input type="text" id="unite" name="unite" required value="<%=request.getAttribute("ingrediant") != null ? ((Ingrediant) request.getAttribute("ingrediant")).getUnite() : ""%>"><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description"><%=request.getAttribute("ingrediant") != null ? ((Ingrediant) request.getAttribute("ingrediant")).getDescription() : ""%></textarea><br>

        <button type="submit"><%=request.getAttribute("mode") == null ? "Ajouter" : "Mettre à jour" %></button>
    </form>

    <!-- Tableau des Ingrédients -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Unité</th>
                <th>Prix</th>
                <th>Quantité</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Ingrediant> ingrediants = (List<Ingrediant>) request.getAttribute("ingrediants");
                if (ingrediants != null) {
                    for (Ingrediant ingrediant : ingrediants) {
            %>
                        <tr>
                            <td><%= ingrediant.getId() %></td>
                            <td><%= ingrediant.getNom() %></td>
                            <td><%= ingrediant.getUnite() %></td>
                            <td><%= ingrediant.getPrix() %></td>
                            <td><%= ingrediant.getQuantite() %></td>
                            <td><%= ingrediant.getDescription() %></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/admin/ingrediant/modifier?id=<%= ingrediant.getId() %>">
                                    <button type="button">Modifier</button>
                                </a>
                                <a href="<%= request.getContextPath() %>/admin/ingrediant/supprimer?id=<%= ingrediant.getId() %>">
                                    <button type="button" class="btn-delete">Supprimer</button>
                                </a>
                            </td>
                        </tr>
            <% 
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>
