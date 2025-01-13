<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%
        String role = (String)request.getAttribute("role");
        if(role != null && role.equals("admin")){
            %>
            <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-admin.css">
            <%
        }else{
            %>
            <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-client.css">
            <%
        }
    %>
    <title>Boulangerie Delice</title>
    <script src="<%=request.getContextPath()%>/assets/js/sidebar.js" defer></script>
</head>
<body>
    <div class="layout">
        <!-- Sidebar -->
        <jsp:include page="/WEB-INF/views/template/sidebar.jsp" />

        <!-- Main Content -->
        <div class="main-content">
            <!-- Header -->
            <jsp:include page="/WEB-INF/views/template/header.jsp" />
            
            <!-- Dynamic Content -->
            <div class="content">
                <jsp:include page="/WEB-INF/views/template/content.jsp" />
            </div>
            
            <!-- Footer -->
            <jsp:include page="/WEB-INF/views/template/footer.jsp" />
        </div>
    </div>
</body>
</html>
