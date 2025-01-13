package mg.itu.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.itu.model.stock.StockProduit;

@Repository
public interface StockProduitRepository extends JpaRepository<StockProduit, Long> {

    @Query("SELECT s FROM StockProduit s WHERE "
         + "(:idProduit IS NULL OR s.produit.id = :idProduit) AND "
         + "(:dateDebut IS NULL OR s.dateTransaction >= :dateDebut) AND "
         + "(:dateFin IS NULL OR s.dateTransaction <= :dateFin) "
         + "ORDER BY s.dateTransaction DESC")
    List<StockProduit> findByFilters(@Param("idProduit") Long idProduit, 
                                     @Param("dateDebut") String dateDebut, 
                                     @Param("dateFin") String dateFin);

    @Query("SELECT s.produit.nom, SUM(s.entree - s.sortie) as quantity "
         + "FROM StockProduit s "
         + "GROUP BY s.produit.nom")
    List<Object[]> findProductQuantities();

    default Map<String, Double> getProductQuantities() {
        return findProductQuantities().stream()
            .collect(Collectors.toMap(
                result -> (String) result[0],
                result -> (Double) result[1]
            ));
    }
}
