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
import mg.itu.model.categorie.IngrediantCategorie;
import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Ingrediant;
import mg.itu.repository.CategorieRepository;
import mg.itu.repository.IngrediantCategorieRepository;
import mg.itu.repository.IngrediantRepository;

import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/ingrediant-categorie")
public class AdminIngrediantCategorieController {

    @Autowired
    private IngrediantCategorieRepository ingrediantCategorieRepository;

    @Autowired
    private IngrediantRepository ingrediantRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @GetMapping
    public String getIngrediantCategories(Model model) {
        List<IngrediantCategorie> ingrediantCategories = ingrediantCategorieRepository.findAll();
        model.addAttribute("ingrediantCategories", ingrediantCategories);

        List<Ingrediant> ingrediants = ingrediantRepository.findAll();
        model.addAttribute("ingrediants", ingrediants);

        List<Categorie> categories = categorieRepository.findByTypeCategorie_Nom("ingrediant");
        model.addAttribute("categories", categories);

        model.addAttribute("page", "admin/categorie/ingrediant-categorie");
        return "template/template";
    }

    @PostMapping("/sauvegarder")
    public String sauvegarderIngrediantCategorie(
            @RequestParam Long idIngrediant, 
            @RequestParam Long idCategorie, 
            RedirectAttributes redirectAttributes) {
        try {
            Ingrediant ingrediant = ingrediantRepository.findById(idIngrediant).orElseThrow(() -> new RuntimeException("Ingrédient introuvable"));
            Categorie categorie = categorieRepository.findById(idCategorie).orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

            IngrediantCategorie ingrediantCategorie = new IngrediantCategorie();
            ingrediantCategorie.setIngrediant(ingrediant);
            ingrediantCategorie.setCategorie(categorie);

            ingrediantCategorieRepository.save(ingrediantCategorie);
            redirectAttributes.addFlashAttribute("success", "Association ajoutée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout de l'association.");
        }
        return "redirect:/admin/ingrediant-categorie";
    }

    @GetMapping("/supprimer")
    public String supprimerIngrediantCategorie(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            ingrediantCategorieRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Association supprimée avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression.");
        }
        return "redirect:/admin/ingrediant-categorie";
    }
}
