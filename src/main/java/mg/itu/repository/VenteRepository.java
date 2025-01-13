package mg.itu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mg.itu.model.vente.Vente;

import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {

    @Query("SELECT v FROM Vente v WHERE v.dateVente BETWEEN :dateDebut AND :dateFin")
    List<Vente> findAllByDateRange(String dateDebut, String dateFin);
    
}
