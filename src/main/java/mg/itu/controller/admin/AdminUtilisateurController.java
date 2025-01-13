package mg.itu.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.auth.Role;
import mg.itu.model.auth.Utilisateur;
import mg.itu.repository.RoleRepository;
import mg.itu.repository.UtilisateurRepository;
import mg.itu.service.auth.AuthService;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin")
public class AdminUtilisateurController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthService authService;

    @GetMapping("/utilisateur")
    public String getCrudUtilisateur(Long id, String nom, Model model) {
        List<Utilisateur> utilisateurs = null;
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);

        if (nom != null && nom.isEmpty()) {
            utilisateurs = utilisateurRepository.findAllByNomLike("%" + nom + "%");
        } else {
            utilisateurs = utilisateurRepository.findAll();
        }

        if (id != null) {
            Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
            model.addAttribute("utilisateur", utilisateur);
        }

        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("page", "admin/utilisateur");
        return "template/template";  // Assurez-vous que le chemin correspond à votre structure
    }

    @PostMapping("/utilisateur/sauvegarder")
    public String sauvegarderUtilisateur(Long id, String nom, String email, String mdp, Long idRole,RedirectAttributes redirectAttributes) {
        try {
            authService.addUtilisateur(id, nom, email, mdp, idRole);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'insertion de l'utilisateur");
        }
        return "redirect:/admin/utilisateur";
    }
}
