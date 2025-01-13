<%@ page import="mg.itu.model.auth.Utilisateur" %>

<%
    // Récupérer l'utilisateur à partir de la session
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    
    // Vérifier si l'utilisateur est connecté
    boolean isLoggedIn = (utilisateur != null);
    
    // Récupérer le rôle de l'utilisateur
    String role = isLoggedIn ? utilisateur.getRole().getNom() : null;
%>

<div class="header">
    <%
        if (role != null) {
            %>
            <div class="userinfo">
                <%
                if (role.equals("admin")) {
                    %>
                    <img src="<%=request.getContextPath()%>/assets/images/adminIcon.png" alt="Admin Icon">
                    <%
                } else if (role.equals("client")) {
                    %>
                    <img src="<%=request.getContextPath()%>/assets/images/userIcon.png" alt="User Icon">
                    <%
                }
                %>
                <%= utilisateur.getNom() %>
            </div>
            <%
        } else {
            %>
            <div>Boulangerie Delice</div>
            <%
        }
    %>
    <nav>
        <% if (isLoggedIn) { %>
            <a href="profile">Profil</a>
            <a href="<%=request.getContextPath()%>/logout">Deconnexion</a>

        <% } else { %>
            <a href="<%=request.getContextPath()%>/login">Connexion</a>
            <a href="<%=request.getContextPath()%>/register">Inscription</a>
        <% } %>
    </nav>
</div>
