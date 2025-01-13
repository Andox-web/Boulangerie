package mg.itu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mg.itu.model.fabrication.Recette;

public interface RecetteRepository extends JpaRepository<Recette,Long>{
    @Query(value =  "SELECT * FROM recette WHERE idProduit = :idProduit Order By dateCreation Desc LIMIT 1", nativeQuery = true)
    public Optional<Recette> findByProduit_IdOrderBydateCreationDesc(@Param("idProduit")Long idProduit);
    
}
