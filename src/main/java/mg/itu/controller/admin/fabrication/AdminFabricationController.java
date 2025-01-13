package mg.itu.controller.admin.fabrication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Produit;
import mg.itu.model.categorie.Categorie;
import mg.itu.model.fabrication.Fabrication;
import mg.itu.repository.CategorieRepository;
import mg.itu.repository.IngrediantRepository;
import mg.itu.repository.ProduitRepository;
import mg.itu.service.FabricationService;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/fabrication/fabrication")
public class AdminFabricationController {
    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private IngrediantRepository ingrediantRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private FabricationService fabricationService;
    
    @GetMapping
    public String afficherTransactions(
            @RequestParam(required = false) Long idCategorie,
            @RequestParam(required = false) Long idIngrediant,
            Model model) {
        
        List<Produit> produits = produitRepository.findAll();
        List<Categorie> categories = categorieRepository.findByTypeCategorie_Nom("produit");
        
        List<Fabrication> transactions = fabricationService.getFabrications(idCategorie, idIngrediant);
        
        model.addAttribute("produits", produits);
        model.addAttribute("categories", categories);
        model.addAttribute("ingrediants", ingrediantRepository.findAll());
        model.addAttribute("transactions", transactions);
        model.addAttribute("page", "admin/fabrication/fabrication");
        return "template/template";
    }


    @PostMapping("/ajouter")
    public String ajouterTransaction(
            @RequestParam Long idProduit,
            @RequestParam(required = false,defaultValue = "0") Double quantite,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        try {
            fabricationService.saveFabrication(idProduit, quantite);   
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/fabrication/fabrication";
    }
}
