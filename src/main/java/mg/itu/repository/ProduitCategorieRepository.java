package mg.itu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.model.categorie.ProduitCategorie;

public interface ProduitCategorieRepository  extends JpaRepository<ProduitCategorie,Long>{
    
}
