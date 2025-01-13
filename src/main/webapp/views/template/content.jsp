<% 
    String pagetemplate = (String) request.getAttribute("page");
    request.getRequestDispatcher("/WEB-INF/views/" + pagetemplate + ".jsp").include(request, response);
%>