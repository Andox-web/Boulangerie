package mg.itu.repository;


import mg.itu.model.parfum.ProduitParfum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitParfumRepository extends JpaRepository<ProduitParfum, Long> {
}
