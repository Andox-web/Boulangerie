package mg.itu.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.itu.model.Recommendation;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    @Query(name =  """
            select * from recommendation 
            where ( dateDebut <= CAST(:debut AS TIMESTAMP) and CAST(:debut AS TIMESTAMP) <= dateFin) or 
            ( dateDebut <= CAST(:fin AS TIMESTAMP) and CAST(:fin AS TIMESTAMP) <= dateFin) 
        """ , nativeQuery = true)
    List<Recommendation> findAllByDateDebutAndDateFin(@Param("debut") LocalDateTime debut,@Param("fin") LocalDateTime fin);
}
