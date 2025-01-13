package mg.itu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.itu.model.Ingrediant;

@Repository
public interface IngrediantRepository extends JpaRepository<Ingrediant,Long>{
    List<Ingrediant> findByNomContainingOrDescriptionContaining(String nom, String description);

    @Query(value = "SELECT prix FROM prixingrediant WHERE idIngrediant = :id AND dateprix <= now() ORDER BY dateprix desc", nativeQuery = true)
    Optional<Double> findPrixOptional(@Param("id") Long id);

    @Query(value = "SELECT SUM(entree-sortie) FROM stockIngrediant s  WHERE idIngrediant = :id AND datetransaction <= now()", nativeQuery = true)
    Optional<Double> findQuantiteOptional(@Param("id") Long id);

}
