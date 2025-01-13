package mg.itu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.model.categorie.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    List<Categorie> findByTypeCategorie_Nom(String nomTypeCategorie);
}
