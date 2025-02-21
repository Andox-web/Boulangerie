package mg.itu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mg.itu.model.vente.Vendeur;

@Repository
public interface VendeurRepository extends JpaRepository<Vendeur, Long> {
    
}
