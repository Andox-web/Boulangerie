package mg.itu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mg.itu.model.vente.CommissionVente;

@Repository
public interface CommissionVenteRepository extends JpaRepository<CommissionVente, Long> {
    
    @Query(value = "select valeur from commission order by dateCommission limit 1", nativeQuery = true)
    Optional<Double> commissionValue();
}
