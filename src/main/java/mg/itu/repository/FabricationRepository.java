package mg.itu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mg.itu.model.fabrication.Fabrication;

public interface FabricationRepository extends JpaRepository<Fabrication,Long> {

    List<Fabrication> findByProduit_Id(Long idProduit);
    
    @Query(value = """
    SELECT f.* FROM fabrication f join 
    (select r.* from recette r join detailrecette dc on r.id = dc.idRecette where r.idProduit = :idProduit and dc.idIngrediant = :idIngrediant)
     as re on f.idRecette = re.id order by f.datefabrication desc
    """ , nativeQuery=true)
    public List<Fabrication> findFabricationByProduitAndIngrediant(@Param("idProduit")Long idRecette,@Param("idIngrediant")Long idIngrediant);

    @Query(value = """ 
    SELECT f.* FROM fabrication f join 
    (select r.* from recette r where r.idProduit = :idProduit) as re on f.idRecette = re.id order by f.datefabrication desc
    """ , nativeQuery=true)
    public List<Fabrication> findFabricationByProduit(@Param("idProduit")Long idProduit); 
    
    @Query(value = """
    SELECT f.* FROM fabrication f 
    join (select r.* FROM recette r join detailrecette dc on r.id = dc.idRecette where dc.idIngrediant = :idIngrediant) 
    as re on f.idRecette = re.id order by f.datefabrication desc
    """ , nativeQuery=true)
    public List<Fabrication> findFabricationByIngrediant(@Param("idIngrediant")Long idIngrediant);
}
