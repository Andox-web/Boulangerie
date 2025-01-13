package mg.itu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.itu.model.auth.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{
    Role getByNom(String nom);
}
