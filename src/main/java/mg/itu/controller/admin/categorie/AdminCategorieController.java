package mg.itu.controller.admin.categorie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.categorie.Categorie;
import mg.itu.model.categorie.TypeCategorie;
import mg.itu.repository.CategorieRepository;
import mg.itu.repository.TypeCategorieRepository;

import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/categorie")
public class AdminCategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private TypeCategorieRepository typeCategorieRepository;

    @GetMapping
    public String getCategories(Long id, Model model) {
        List<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);

        List<TypeCategorie> typesCategorie = typeCategorieRepository.findAll();
        model.addAttribute("typesCategorie", typesCategorie);

        if (id != null) {
            Categorie categorie = categorieRepository.findById(id).orElse(null);
            model.addAttribute("categorie", categorie);
        }

        model.addAttribute("page", "admin/categorie/categorie");
        return "template/template";
    }

    @PostMapping("/sauvegarder")
    public String sauvegarderCategorie(Long id, String nom, String description, Long idTypeCategorie, RedirectAttributes redirectAttributes) {
        try {
            Categorie categorie = (id != null) ? categorieRepository.findById(id).orElse(new Categorie()) : new Categorie();
            categorie.setNom(nom);
            categorie.setDescription(description);

            TypeCategorie typeCategorie = (idTypeCategorie != null) ? typeCategorieRepository.findById(idTypeCategorie).orElse(null) : null;
            categorie.setTypeCategorie(typeCategorie);

            categorieRepository.save(categorie);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'enregistrement de la catégorie.");
        }
        return "redirect:/admin/categorie";
    }
}
