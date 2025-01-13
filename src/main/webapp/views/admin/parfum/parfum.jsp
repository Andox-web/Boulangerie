<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.parfum.Parfum" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Parfums</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
    <h1>Gestion des Parfums</h1>
    <%
        Parfum parfum = (Parfum) request.getAttribute("parfum");
        boolean isEdit = (parfum != null);
    %>
    <form action="<%=request.getContextPath()%>/admin/parfum/sauvegarder" method="post">
        <% if (isEdit) { %>
            <input type="hidden" name="id" value="<%= parfum.getId() %>">
        <% } %>
        <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" value="<%= isEdit ? parfum.getNom() : "" %>" required>
        
        <label for="description">Description :</label>
        <textarea name="description" id="description"><%= isEdit ? parfum.getDescription() : "" %></textarea>
        
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

    <h2>Liste des Parfums</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% List<Parfum> parfums = (List<Parfum>) request.getAttribute("parfums"); 
               for (Parfum p : parfums) { %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getNom() %></td>
                    <td><%= p.getDescription() %></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/admin/parfum/supprimer?id=<%= p.getId() %>">Supprimer</a>
                        <a href="<%=request.getContextPath()%>/admin/parfum?id=<%= p.getId() %>">Modifier</a>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
