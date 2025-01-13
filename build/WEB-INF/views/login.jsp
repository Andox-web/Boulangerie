<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>
    
    <link rel="stylesheet" href="assets/css/style-client.css">
    <link rel="stylesheet" href="assets/css/style-form.css">
</head>
<body>
    <div class="content">
        <div class="form-container">
            <h1>Connexion</h1>
            <form action="login" method="POST">
                <div class="input-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required placeholder="Entrez votre email">
                </div>
                <div class="input-group">
                    <label for="motDePass">Mot de passe</label>
                    <input type="password" id="motDePass" name="motDePass" required placeholder="Entrez votre mot de passe">
                </div>
                <button type="submit" class="btn-submit">Se connecter</button>
            </form>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <p class="error"><%= error %></p>
            <%
                }
            %>

            <p>Pas encore de compte ? <a href="register">Inscrivez-vous ici</a></p>
        </div>
    </div>
</body>
</html>
