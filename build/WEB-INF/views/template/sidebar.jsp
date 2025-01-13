<aside class="sidebar">
    <div class="sidebar-logo">
        <img src="<%=request.getContextPath()%>/assets/images/logo.png" alt="Boulangerie Delice" />
    </div>
    <ul class="nav-menu">
        <%
            String role = (String)request.getAttribute("role");
            if(role != null && role.equals("admin")){
                %>
                    <li><a href="<%=request.getContextPath()%>/admin/dashboard">Dashboard</a></li>
                    <li><a href="<%=request.getContextPath()%>/admin/vente">vente</a></li>
                    <li>
                        <a class="list">Fabrication <span class="dots-vertical"></span></a>
                        <div class="submenu">
                            <ul>
                                <li><a href="<%=request.getContextPath()%>/admin/fabrication/fabrication">fabrication</a></li>
                                <li>
                                    <a class="list">Recette <span class="dots-vertical"></span></a>
                                    <div class="submenu">
                                        <ul>
                                            <li><a href="<%=request.getContextPath()%>/admin/fabrication/recette/liste">Liste</a></li>
                                            <li><a href="<%=request.getContextPath()%>/admin/fabrication/recette/ajout">Ajout</a></li>
                                        </ul>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <a class="list">Crud <span class="dots-vertical"></span></a>
                        <div class="submenu">
                            <ul>
                                <li><a href="<%=request.getContextPath()%>/admin/utilisateur">Utilisateur</a></li>
                                <li><a href="<%=request.getContextPath()%>/admin/produit">Produit</a></li>
                                <li><a href="<%=request.getContextPath()%>/admin/ingrediant">Ingrediant</a></li>
                                <li>
                                    <a class="list">Categorie <span class="dots-vertical"></span></a>
                                    <div class="submenu">
                                        <ul>
                                            <li><a href="<%=request.getContextPath()%>/admin/categorie/typeCategorie">Type Categorie</a></li>
                                            <li><a href="<%=request.getContextPath()%>/admin/categorie">Categorie</a></li>
                                            <li><a href="<%=request.getContextPath()%>/admin/categorie/produit-categorie">Produit Categorie</a></li>
                                            <li><a href="<%=request.getContextPath()%>/admin/ingrediant-categorie">Ingrediant Categorie</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <a class="list">Stock <span class="dots-vertical"></span></a>
                                    <div class="submenu">
                                        <ul>
                                            <li><a href="<%=request.getContextPath()%>/admin/stock/stock-produit">Stock Produits</a>
                                            <li><a href="<%=request.getContextPath()%>/admin/stock/stock-ingrediant">Stock Ingrediant</a>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <a class="list">prix <span class="dots-vertical"></span></a>
                                    <div class="submenu">
                                        <ul>
                                            <li><a href="<%=request.getContextPath()%>/admin/prix-produit">Prix Produit</a></li>
                                            <li><a href="<%=request.getContextPath()%>/admin/prix-ingrediant">Prix Ingrediant</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li>
                                    <a class="list">Parfum <span class="dots-vertical"></span></a>
                                    <div class="submenu">
                                        <ul>
                                            <li><a href="<%=request.getContextPath()%>/admin/parfum">Parfum</a></li>
                                            <li><a href="<%=request.getContextPath()%>/admin/produit-parfum">Produit Parfum</a></li>
                                        </ul>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        
                    </li>
                <%
            }else if(role != null && role.equals("client")){
                %>
                <li><a href="<%=request.getContextPath()%>">Produits</a></li>
                <li><a href="<%=request.getContextPath()%>/panier">Voir panier</a></li>
                <%
            }
            else{
                %>
                <li><a href="<%=request.getContextPath()%>">Produits</a></li>
                
                <%
            }
        %>
    </ul>
</aside>
