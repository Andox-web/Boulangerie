package mg.itu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.model.auth.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long>{
    boolean existsByEmail(String email);
    
    Utilisateur findByEmail(String email);

    List<Utilisateur> findAllByNomLike(String nom);
}
