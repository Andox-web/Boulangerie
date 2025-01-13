package mg.itu.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.itu.model.vente.DetailVente;
import mg.itu.model.Produit;

@Repository
public interface DetailVenteRepository extends JpaRepository<DetailVente, Long> {

    @Query(value = "SELECT DATE(v.datevente) as date, p.nom as product, SUM(dv.quantite) as quantity "
                 + "FROM detailvente dv "
                 + "JOIN vente v ON dv.idvente = v.id "
                 + "JOIN produit p ON dv.idproduit = p.id "
                 + "WHERE (:dateDebut IS NULL OR v.datevente >= CAST(:dateDebut AS TIMESTAMP)) AND "
                 + "(:dateFin IS NULL OR v.datevente <= CAST(:dateFin AS TIMESTAMP)) "
                 + "GROUP BY DATE(v.datevente), p.nom "
                 + "ORDER BY DATE(v.datevente), p.nom", nativeQuery = true)
    List<Object[]> findSalesDataGroupedByDateAndProduct(@Param("dateDebut") String dateDebut, 
                                                        @Param("dateFin") String dateFin);

    @Query(value = "SELECT DATE(v.datevent6e) as date, p.nom as product, SUM(dv.total) as total "
                 + "FROM detailvente dv "
                 + "JOIN vente v ON dv.idvente = v.id "
                 + "JOIN produit p ON dv.idproduit = p.id "
                 + "WHERE (:dateDebut IS NULL OR v.datevente >= CAST(:dateDebut AS TIMESTAMP)) AND "
                 + "(:dateFin IS NULL OR v.datevente <= CAST(:dateFin AS TIMESTAMP)) "
                 + "GROUP BY DATE(v.datevente), p.nom "
                 + "ORDER BY DATE(v.datevente), p.nom", nativeQuery = true)
    List<Object[]> findTotalSalesDataGroupedByDateAndProduct(@Param("dateDebut") String dateDebut, 
                                                             @Param("dateFin") String dateFin);

    @Query(value = "SELECT p.nom, SUM(dv.quantite) as quantity "
                 + "FROM detailvente dv "
                 + "JOIN produit p ON dv.idproduit = p.id "
                 + "GROUP BY p.id "
                 + "ORDER BY quantity DESC "
                 + "LIMIT :limit", nativeQuery = true)
    List<Object[]> findMostSoldProducts(@Param("limit") Integer limit);

    default Map<String, Map<String, List<Double>>> getSalesData(String dateDebut, String dateFin) {
        List<Object[]> results = findSalesDataGroupedByDateAndProduct(dateDebut, dateFin);
        Map<String, Map<String, List<Double>>> salesData = new HashMap<>();
    
        for (Object[] result : results) {
            java.sql.Date date = (java.sql.Date) result[0]; // Changer le type
            String product = (String) result[1];
            Double quantity = ((BigDecimal) result[2]).doubleValue();
    
            // Convertir la date en String si nécessaire
            String dateStr = date.toString();
    
            salesData
                .computeIfAbsent(dateStr, k -> new HashMap<>())
                .computeIfAbsent(product, k -> new ArrayList<>())
                .add(quantity);
        }
    
        return salesData;
    }
    
    default Map<String, Map<String, List<Double>>> getTotalSalesData(String dateDebut, String dateFin) {
        List<Object[]> results = findTotalSalesDataGroupedByDateAndProduct(dateDebut, dateFin);
        Map<String, Map<String, List<Double>>> totalSalesData = new HashMap<>();
    
        for (Object[] result : results) {
            java.sql.Date date = (java.sql.Date) result[0]; // Changer le type
            String product = (String) result[1];
            Double total = ((BigDecimal) result[2]).doubleValue();
    
            // Convertir la date en String si nécessaire
            String dateStr = date.toString();
    
            totalSalesData
                .computeIfAbsent(dateStr, k -> new HashMap<>())
                .computeIfAbsent(product, k -> new ArrayList<>())
                .add(total);
        }
    
        return totalSalesData;
    }
    
}
