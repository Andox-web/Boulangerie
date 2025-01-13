package mg.itu.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import mg.itu.model.auth.Utilisateur;
import mg.itu.service.auth.AuthService;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Retourner le formulaire d'inscription
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String nom, @RequestParam String email, 
                               @RequestParam String motDePass, RedirectAttributes redirectAttributes) {
        
        try {
            authService.registerUser(nom, email, motDePass);   
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("nom", nom);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/register";
        }
        
        redirectAttributes.addFlashAttribute("success", "Inscription réussie ! Veuillez vous connecter");
        return "redirect:/login"; 
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String motDePass, RedirectAttributes model,HttpSession session) {
        Utilisateur utilisateur = authService.loginUser(email, motDePass);
        if (utilisateur != null) {
            session.setAttribute("utilisateur", utilisateur);
            return "redirect:/"; // Rediriger vers une page d'accueil ou tableau de bord
        } else {
            model.addFlashAttribute("error", "Email ou mot de passe incorrect.");
            model.addFlashAttribute("email", email);
            return "redirect:/login"; // Retourner la page de connexion avec un message d'erreur
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("utilisateur");
        return "redirect:/";
    }
}
