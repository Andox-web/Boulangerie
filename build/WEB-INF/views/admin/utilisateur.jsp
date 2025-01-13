<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.auth.Role" %>
<%@ page import="mg.itu.model.auth.Utilisateur" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Utilisateurs</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Utilisateurs</h1>

    <!-- Formulaire d'Ajout/Modification -->
    <form method="POST" action="<%=request.getContextPath()%>/admin/utilisateur/sauvegarder">
        <input type="hidden" name="id" value="<%= request.getAttribute("utilisateur") != null ? ((Utilisateur) request.getAttribute("utilisateur")).getId() : "" %>">
        <input type="text" name="nom" placeholder="Nom" value="<%= request.getAttribute("utilisateur") != null ? ((Utilisateur) request.getAttribute("utilisateur")).getNom() : "" %>">
        <input type="email" name="email" placeholder="Email" value="<%= request.getAttribute("utilisateur") != null ? ((Utilisateur) request.getAttribute("utilisateur")).getEmail() : "" %>">
        <input type="password" name="mdp" placeholder="Mot de pass" required>
        <!-- Sélection des rôles -->
        <select name="idRole">
            <% 
                List<Role> roles = (List<Role>) request.getAttribute("roles");
                if (roles != null) {
                    for (Role role : roles) {
                        Long selectedRoleId = request.getAttribute("utilisateur") != null ? 
                            ((Utilisateur) request.getAttribute("utilisateur")).getRole().getId() : null;
            %>
                        <option value="<%= role.getId() %>" <%= (selectedRoleId != null && selectedRoleId.equals(role.getId())) ? "selected" : "" %>>
                            <%= role.getNom() %>
                        </option>
            <% 
                    }
                }
            %>
        </select>

        <button type="submit">Enregistrer</button>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p class="error"><%= error %></p>
        <%
            }
        %>
    </form>

    <!-- Formulaire de Recherche -->
    <form method="GET" action="#">
        <input type="text" name="nom" placeholder="Rechercher un utilisateur...">
        <button type="submit">Rechercher</button>
    </form>
    
    <!-- Tableau des Utilisateurs -->
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Email</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
                if (utilisateurs != null) {
                    for (Utilisateur utilisateur : utilisateurs) {
            %>
                        <tr>
                            <td><%= utilisateur.getId() %></td>
                            <td><%= utilisateur.getNom() %></td>
                            <td><%= utilisateur.getEmail() %></td>
                            <td><%= utilisateur.getRole().getNom() %></td>
                            <td>
                                <a href="?id=<%= utilisateur.getId() %>">
                                    <button type="button" class="btn-modify">Modifier</button>
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
