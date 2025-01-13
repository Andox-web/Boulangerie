package mg.itu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.model.parfum.Parfum;

public interface ParfumRepository extends JpaRepository<Parfum, Long> {
    
}
