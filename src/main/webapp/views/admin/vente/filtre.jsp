<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.auth.Utilisateur" %>
<%@ page import="mg.itu.model.Produit" %>
<%@ page import="mg.itu.model.vente.Vente" %>
<%@ page import="mg.itu.model.vente.DetailVente" %>
<%@ page import="mg.itu.model.categorie.Categorie" %>
<%@ page import="mg.itu.model.parfum.Parfum" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="mg.itu.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter Vente</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
   
    <h2>Filtrer les Ventes</h2>
    <form action="<%=request.getContextPath()%>/admin/vente/filtre" method="get">
        <label for="dateStr">Date :</label>
        <input type="date" name="dateStr" id="">
        <button type="submit">Filtrer</button>
    </form>

    <h2>les utilisateur ayant achete</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Email</th>
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
                        </tr>
            <% 
                    }
                }
            %>
        </tbody>
    </table>

    <h2>Liste des Ventes correspondants</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Utilisateur</th>
                <th>Total</th>
                <th>Date de Vente</th>
                <th>Details de la Vente</th>
            </tr>
        </thead>
        <tbody>
            <% List<Vente> ventes = (List<Vente>) request.getAttribute("ventes"); 
               for (Vente vente : ventes) { %>
                <tr>
                    <td><%= vente.getId() %></td>
                    <td><%= vente.getUtilisateur()!=null?vente.getUtilisateur().getNom():" " %></td>
                    <td><%= vente.getTotal() %> </td>
                    <td><%= DateUtil.formatToDate(vente.getDateVente()) %></td>
                    <td>
                        <table>
                            <thead>
                                <tr>
                                    <th>Produit</th>
                                    <th>Quantite</th>
                                    <th>Prix Unitaire</th>
                                    <th>Total</th>
                                    <th>Categories</th>
                                    <th>Parfums</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (DetailVente detail : vente.getDetailVentes()) { %>
                                    <tr>
                                        <td><%= detail.getProduit().getNom() %></td>
                                        <td><%= detail.getQuantite() %></td>
                                        <td><%= detail.getPrixUnitaire() %></td>
                                        <td><%= detail.getTotal() %></td>
                                        <td><%= detail.getProduit().getProduitCategories().isEmpty() ? "Aucun" : detail.getProduit().getProduitCategories().stream()
                                            .map(pc -> pc.getCategorie().getNom())
                                            .collect(Collectors.joining(", ")) %></td>
                                        <td><%= detail.getProduit().getProduitParfums().isEmpty() ? "Nature" : detail.getProduit().getProduitParfums().stream()
                                            .map(pp -> pp.getParfum().getNom())
                                            .collect(Collectors.joining(", ")) %></td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>