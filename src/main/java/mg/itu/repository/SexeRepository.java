package mg.itu.repository;

import mg.itu.model.vente.Sexe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexeRepository extends JpaRepository<Sexe,Long>{
    
}
