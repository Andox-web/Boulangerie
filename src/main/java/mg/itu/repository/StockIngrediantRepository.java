package mg.itu.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.itu.model.stock.StockIngrediant;

@Repository
public interface StockIngrediantRepository extends JpaRepository<StockIngrediant, Long> {

    @Query("SELECT s FROM StockIngrediant s WHERE "
         + "(:idIngrediant IS NULL OR s.ingrediant.id = :idIngrediant) AND "
         + "(:dateDebut IS NULL OR s.dateTransaction >= :dateDebut) AND "
         + "(:dateFin IS NULL OR s.dateTransaction <= :dateFin) "
         + "ORDER BY s.dateTransaction DESC")
    List<StockIngrediant> findByFilters(@Param("idIngrediant") Long idIngrediant, 
                                        @Param("dateDebut") String dateDebut, 
                                        @Param("dateFin") String dateFin);

    @Query("SELECT s.ingrediant.nom, SUM(s.entree - s.sortie) as quantity "
         + "FROM StockIngrediant s "
         + "GROUP BY s.ingrediant.nom")
    List<Object[]> findIngrediantQuantities();

    default Map<String, Double> getIngrediantQuantities() {
        return findIngrediantQuantities().stream()
            .collect(Collectors.toMap(
                result -> (String) result[0],
                result -> (Double) result[1]
            ));
    }
}
