package mg.itu.controller.admin.categorie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mg.itu.model.categorie.Categorie;
import mg.itu.model.categorie.ProduitCategorie;
import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Produit;
import mg.itu.repository.CategorieRepository;
import mg.itu.repository.ProduitCategorieRepository;
import mg.itu.repository.ProduitRepository;

import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/categorie/produit-categorie")
public class AdminProduitCategorieController {

    @Autowired
    private ProduitCategorieRepository produitCategorieRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping
    public String getProduitCategories(Model model) {
        List<ProduitCategorie> produitCategories = produitCategorieRepository.findAll();
        model.addAttribute("produitCategories", produitCategories);

        List<Produit> produits = produitRepository.findAll();
        model.addAttribute("produits", produits);

        List<Categorie> categories = categorieRepository.findByTypeCategorie_Nom("produit");
        model.addAttribute("categories", categories);

        model.addAttribute("page", "admin/categorie/produit-categorie");
        return "template/template";
    }

    @PostMapping("/sauvegarder")
    public String sauvegarderProduitCategorie(
            @RequestParam Long idProduit, 
            @RequestParam Long idCategorie, 
            RedirectAttributes redirectAttributes) {
        try {
            Produit produit = produitRepository.findById(idProduit).orElseThrow(() -> new RuntimeException("Produit introuvable"));
            Categorie categorie = categorieRepository.findById(idCategorie).orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

            ProduitCategorie produitCategorie = new ProduitCategorie();
            produitCategorie.setProduit(produit);
            produitCategorie.setCategorie(categorie);

            produitCategorieRepository.save(produitCategorie);
            redirectAttributes.addFlashAttribute("success", "Association ajoutée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout de l'association.");
        }
        return "redirect:/admin/produit-categorie";
    }

    @GetMapping("/supprimer")
    public String supprimerProduitCategorie(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            produitCategorieRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Association supprimée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression.");
        }
        return "redirect:/admin/categorie/produit-categorie";
    }
}
