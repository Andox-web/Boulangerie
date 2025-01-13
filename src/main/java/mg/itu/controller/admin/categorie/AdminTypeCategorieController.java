package mg.itu.controller.admin.categorie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.categorie.TypeCategorie;
import mg.itu.repository.TypeCategorieRepository;

import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/categorie/typeCategorie")
public class AdminTypeCategorieController {

    @Autowired
    private TypeCategorieRepository TypeCategorieRepository;

    @GetMapping
    public String getCategories(Long id, Model model) {
        List<TypeCategorie> categories = TypeCategorieRepository.findAll();
        model.addAttribute("categories", categories);

        if (id != null) {
            TypeCategorie categorie = TypeCategorieRepository.findById(id).orElse(null);
            model.addAttribute("categorie", categorie);
        }
        model.addAttribute("page", "admin/categorie/typeCategorie");
        return "template/template";
    }

    @PostMapping("/sauvegarder")
    public String sauvegarderCategorie(Long id, String nom, RedirectAttributes redirectAttributes) {
        try {
            TypeCategorie categorie = (id != null) ? TypeCategorieRepository.findById(id).orElse(new TypeCategorie()) : new TypeCategorie();
            categorie.setNom(nom);
            TypeCategorieRepository.save(categorie);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'enregistrement de la catégorie.");
        }
        return "redirect:/admin/categorie/typeCategorie";
    }
}
