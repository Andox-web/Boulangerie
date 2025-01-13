package mg.itu.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Ingrediant;
import mg.itu.repository.IngrediantRepository;

import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/ingrediant")
public class AdminIngrediantController {

    @Autowired
    private IngrediantRepository ingrediantRepository;

    @GetMapping
    public String getIngrediants(Model model) {
        List<Ingrediant> ingrediants = ingrediantRepository.findAll();
        model.addAttribute("ingrediants", addPrixAndQuantite(ingrediants));
        model.addAttribute("page", "admin/ingrediant");
        return "template/template";
    }

    @PostMapping("/ajouter")
    public String ajouterIngrediant(
            @RequestParam(required = false) Long id,
            @RequestParam String nom,
            @RequestParam String unite,
            @RequestParam(required = false) String description,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (id == null) {  // Ajout d'un nouvel ingrédient
            Ingrediant ingrediant = new Ingrediant();
            ingrediant.setNom(nom);
            ingrediant.setUnite(unite);
            ingrediant.setDescription(description);
            ingrediantRepository.save(ingrediant);
            redirectAttributes.addFlashAttribute("success", "Ingrédient ajouté avec succès.");
        } else {  // Modification d'un ingrédient existant
            Ingrediant ingrediant = ingrediantRepository.findById(id).orElse(null);
            if (ingrediant != null) {
                ingrediant.setNom(nom);
                ingrediant.setUnite(unite);
                ingrediant.setDescription(description);
                ingrediantRepository.save(ingrediant);
                redirectAttributes.addFlashAttribute("success", "Ingrédient modifié avec succès.");
            }
        }

        return "redirect:/admin/ingrediant";
    }

    @GetMapping("/supprimer")
    public String supprimerIngrediant(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            ingrediantRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Ingrédient supprimé avec succès.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression de l'ingrédient.");
        }
        return "redirect:/admin/ingrediant";
    }

    @GetMapping("/modifier")
    public String modifierIngrediant(@RequestParam Long id, Model model) {
        Ingrediant ingrediant = ingrediantRepository.findById(id).orElse(null);
        if (ingrediant != null) {
            model.addAttribute("ingrediant", ingrediant);
            model.addAttribute("mode", "modifier");
            model.addAttribute("page", "admin/ingrediant");
            return "template/template";
        }
        return "redirect:/admin/ingrediant";
    }

    @GetMapping("/recherche")
    public String rechercherIngrediant(@RequestParam String recherche, Model model) {
        List<Ingrediant> ingrediants = ingrediantRepository.findByNomContainingOrDescriptionContaining(recherche, recherche);
        model.addAttribute("ingrediants", addPrixAndQuantite(ingrediants));
        model.addAttribute("page", "admin/ingrediant");
        return "template/template";
    }

    private List<Ingrediant> addPrixAndQuantite(List<Ingrediant> ingrediants){
        for (Ingrediant ingrediant : ingrediants) {
            ingrediant.setQuantite(ingrediantRepository.findQuantiteOptional(ingrediant.getId()).orElse(0.0));
            ingrediant.setPrix(ingrediantRepository.findPrixOptional(ingrediant.getId()).orElse(0.0));
        }
        return ingrediants;
    }
}
