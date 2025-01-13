package mg.itu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mg.itu.model.Produit;
import mg.itu.model.categorie.Categorie;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query(value = """
        SELECT p.* FROM v_produits_prix_recents p
        JOIN produitCategorie pc ON p.id = pc.idProduit
        WHERE pc.idCategorie = :categorie
    """, nativeQuery = true)
    List<Produit> findByIdCategorie(@Param("categorie") Integer categorie);

    @Query(value = """
        SELECT c.* FROM produit p
        JOIN produitCategorie pc ON p.id = pc.idProduit
        JOIN categorie c ON pc.idCategorie = c.id
        WHERE pc.idProduit = :id 
    """, nativeQuery = true)
    List<Categorie> findAllCategorie(@Param("id") Long id);

    // Find by price range
    @Query(value = """
        SELECT * FROM v_produits_prix_recents
        WHERE prix BETWEEN :prixMin AND :prixMax
    """, nativeQuery = true)
    List<Produit> findByPrixBetween(@Param("prixMin") Double prixMin, @Param("prixMax") Double prixMax);

    // Find by category and price range
    @Query(value = """
        SELECT p.* FROM v_produits_prix_recents p
        JOIN produitCategorie pc ON p.id = pc.idProduit
        WHERE pc.idCategorie = :categorie AND p.prix BETWEEN :prixMin AND :prixMax
    """, nativeQuery = true)
    List<Produit> findByCategorieAndPrixBetween(
            @Param("categorie") Integer categorie,
            @Param("prixMin") Double prixMin,
            @Param("prixMax") Double prixMax);

    // Find by name (using LIKE for partial matches)
    @Query(value = """
        SELECT * FROM v_produits_prix_recents
        WHERE nom LIKE %:nom%
    """, nativeQuery = true)
    List<Produit> findByNom(@Param("nom") String nom);

    // Find by name, category, and price range
    @Query(value = """
        SELECT p.* FROM v_produits_prix_recents p
        JOIN produitCategorie pc ON p.id = pc.idProduit
        WHERE p.nom LIKE %:nom% AND pc.idCategorie = :categorie AND p.prix BETWEEN :prixMin AND :prixMax
    """, nativeQuery = true)
    List<Produit> findByNomAndCategorieAndPrixBetween(
            @Param("nom") String nom,
            @Param("categorie") Integer categorie,
            @Param("prixMin") Double prixMin,
            @Param("prixMax") Double prixMax);

    // Find by name and category
    @Query(value = """
        SELECT p.* FROM v_produits_prix_recents p
        JOIN produitCategorie pc ON p.id = pc.idProduit
        WHERE p.nom LIKE %:nom% AND pc.idCategorie = :categorie
    """, nativeQuery = true)
    List<Produit> findByNomAndIdCategorie(@Param("nom") String nom, @Param("categorie") Integer categorie);

    // Find by name and price range
    @Query(value = """
        SELECT * FROM v_produits_prix_recents
        WHERE nom LIKE %:nom% AND prix BETWEEN :prixMin AND :prixMax
    """, nativeQuery = true)
    List<Produit> findByNomAndPrixBetween(@Param("nom") String nom, @Param("prixMin") Double prixMin, @Param("prixMax") Double prixMax);
    
    @Query(value = "SELECT prix FROM v_produits_prix_recents WHERE id = :id", nativeQuery = true)
    Optional<Double> findPrixOptional(@Param("id") Long id);

    @Query(value = "SELECT prix FROM prixProduit WHERE idProduit = :id AND datePrix <= :datePrix order by datePrix desc limit 1 ", nativeQuery = true)
    Optional<Double> findPrixOptional(@Param("id") Long id,@Param("datePrix") LocalDateTime dateTime);

    @Query(value = "SELECT SUM(entree-sortie) FROM stockProduit s  WHERE idProduit = :id AND datetransaction <= now()", nativeQuery = true)
    Optional<Double> findQuantiteOptional(@Param("id") Long id);
}
