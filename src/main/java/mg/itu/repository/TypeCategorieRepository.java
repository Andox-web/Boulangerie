package mg.itu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.itu.model.categorie.TypeCategorie;

@Repository
public interface TypeCategorieRepository extends JpaRepository<TypeCategorie,Long> {
    
}
