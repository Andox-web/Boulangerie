package mg.itu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.itu.model.prix.PrixIngrediant;

@Repository
public interface PrixIngrediantRepository extends JpaRepository<PrixIngrediant,Long>{
    @Query("SELECT p FROM PrixIngrediant p WHERE p.ingrediant.id = :ingrediantId ORDER BY p.datePrix DESC")
    List<PrixIngrediant> findByIngrediantId(@Param("ingrediantId") Long ingrediantId);

    @Query("SELECT p FROM PrixIngrediant p ORDER BY p.datePrix DESC")
    List<PrixIngrediant> findAllOrderByDateDesc();
}
