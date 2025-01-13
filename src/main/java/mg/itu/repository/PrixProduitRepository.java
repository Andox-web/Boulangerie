package mg.itu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.itu.model.prix.PrixProduit;

@Repository
public interface PrixProduitRepository extends JpaRepository<PrixProduit,Long>{
    @Query("SELECT p FROM PrixProduit p WHERE p.produit.id = :produitId ORDER BY p.datePrix DESC")
    List<PrixProduit> findByProduitId(@Param("produitId") Long produitId);

    @Query("SELECT p FROM PrixProduit p ORDER BY p.datePrix DESC")
    List<PrixProduit> findAllOrderByDateDesc();
}
