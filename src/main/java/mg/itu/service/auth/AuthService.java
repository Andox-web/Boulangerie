package mg.itu.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mg.itu.model.auth.Role;
import mg.itu.model.auth.Utilisateur;
import mg.itu.repository.RoleRepository;
import mg.itu.repository.UtilisateurRepository;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public boolean registerUser(String nom, String email, String motDePass) {
        if (utilisateurRepository.existsByEmail(email)) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setEmail(email);
        utilisateur.setMotDePass(motDePass);
        Role role = roleRepository.getByNom("client");
        utilisateur.setRole(role);
        utilisateurRepository.save(utilisateur);

        return true; 
    }

    public Utilisateur loginUser(String email, String motDePass) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur != null && motDePass.equals(utilisateur.getMotDePass())) {
            return utilisateur;
        }
        return null; 
    }

    @Transactional
    public void addUtilisateur(Long id,String Nom,String email,String mdp,Long idRole){
        Utilisateur utilisateur = new Utilisateur();
        if (id!=null) {
            utilisateur = utilisateurRepository.findById(id).orElse(utilisateur);
        }
        Role role = roleRepository.findById(idRole).orElse(null);
        utilisateur.setRole(role);
        utilisateur.setNom(Nom);
        utilisateur.setEmail(email);
        utilisateur.setMotDePass(mdp);
        utilisateurRepository.save(utilisateur);
    }
}
