<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.springframework.validation.BindingResult" %>
<%@ page import="org.springframework.validation.FieldError" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Formulaire Utilisateur</title>
</head>
<body>

    <p><%= request.getAttribute("message")%></p>
    <p><%=request.getAttribute("baseUrl")%></p>
<p id="ye"></p>
<script src="assets\js\function.js"></script>
<script type="module">
    const s= await appelerApi(apiUrl+"/hellojs");
    // Appeler la fonction
    console.log(s);
</script>
</body>
</html>
