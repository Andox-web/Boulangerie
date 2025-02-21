<%@ page import="java.util.List" %>
<%@ page import="mg.itu.model.vente.*" %>
<%@ page import="java.util.*" %>
<%@ page import="mg.itu.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter Vente</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style-crud.css">
</head>
<body>
   
    <h2>Filtrer les Ventes</h2>
    <form action="#" method="get">
        <label for="dateStr">Date :</label>
        <input type="datetime-local" name="dateStr" id="">
        <label for="dateDebutStr">Date debut :</label>
        <input type="datetime-local" name="dateDebutStr" id="">
        <label for="dateFinStr">Date fin :</label>
        <input type="datetime-local" name="dateFinStr" id="">
        <button type="submit">Filtrer</button>
    </form>
    <h2>Commission par sexe</h2>

    <%-- <h3>Commission pour Homme: <%= request.getAttribute("commissionHomme")%></h3>
    <h3>Commission pour Femme: <%= request.getAttribute("commissionFemme")%></h3> --%>
    <%
        Map<Sexe,Double> commissionParSexe =  (Map<Sexe,Double>) request.getAttribute("commissionSexes");
        for (Map.Entry<Sexe,Double> comm : commissionParSexe.entrySet()) {
            %><h3>Commission pour <%=comm.getKey().getNom()%>: <%= comm.getValue()%></h3><%
        }
    %>
    </br>
    <h2>Commission par Vendeurs</h2>
<table>
    <thead>
        <tr>
            <th>Vendeur</th>
            <th>Sexe</th>
            <th>Commission</th>
            <th>Date</th>
        </tr>
    </thead>
    <tbody>
        <% 
            // Récupération de la liste complète des commissions
            List<CommissionVente> commissionVentes = (List<CommissionVente>) request.getAttribute("commissionVentes");

            if (commissionVentes != null && !commissionVentes.isEmpty()) {
                // Initialisation des regroupements par sexe
                Map<String, List<CommissionVente>> groupedBySexe = new HashMap<>();
                for (CommissionVente commissionVente : commissionVentes) {
                    String sexe = commissionVente.getVendeur().getSexe().getNom();
                    groupedBySexe.computeIfAbsent(sexe, k -> new ArrayList<>()).add(commissionVente);
                }

                // Affichage par groupe
                for (Map.Entry<String, List<CommissionVente>> entry : groupedBySexe.entrySet()) {
                    String sexe = entry.getKey();
                    List<CommissionVente> ventes = entry.getValue();
        %>
                    <tr>
                        <td colspan="4" style="font-weight:bold; text-align:center;"><%= sexe %></td>
                    </tr>
        <% 
                    for (CommissionVente vente : ventes) {
        %>
                        <tr>
                            <td><%= vente.getVendeur().getNom() %></td>
                            <td><%= sexe %></td>
                            <td><%= vente.getMontant() %></td>
                            <td><%= vente.getDateCommissionVente() %></td>
                        </tr>
        <% 
                    }
                }
            } else {
        %>
            <tr>
                <td colspan="4" style="text-align:center;">Aucune commission trouvée</td>
            </tr>
        <% 
            }
        %>
    </tbody>
</table>

</body>
</html>