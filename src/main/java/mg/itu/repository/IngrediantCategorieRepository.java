package mg.itu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.itu.model.categorie.IngrediantCategorie;

@Repository
public interface IngrediantCategorieRepository extends JpaRepository<IngrediantCategorie, Long> {
}
