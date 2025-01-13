package mg.itu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Produit;
import mg.itu.model.categorie.Categorie;
import mg.itu.repository.CategorieRepository;
import mg.itu.service.ProduitService;

@Controller
public class IndexController {
    
    @Autowired
    private ProduitService produitService;
    
    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        String role = (String)request.getAttribute("role");
        if (role != null && role.equals("admin")) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/produits";
    }
    
    @GetMapping("/produits")
    @RoleRequired(except = "{admin}")
    public String afficherProduits(
            @RequestParam(required = false) Integer categorie,
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            Model model) {
    
        // Appliquer les filtres et récupérer les produits
        List<Produit> produits = produitService.getProduitsFiltres(null,categorie, prixMin, prixMax);

        List<Categorie> categories = categorieRepository.findAll();

        // Ajouter les produits à la requête
        model.addAttribute("categories", categories);
        model.addAttribute("produits", produits);
        model.addAttribute("page", "produits");

        return "template/template";
    }
}
