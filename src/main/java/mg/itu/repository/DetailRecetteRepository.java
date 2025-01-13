package mg.itu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.model.fabrication.DetailRecette;

public interface DetailRecetteRepository extends JpaRepository<DetailRecette, Long> {
    
}
