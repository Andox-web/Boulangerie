package mg.itu.controller.admin.prix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Ingrediant;
import mg.itu.model.prix.PrixIngrediant;
import mg.itu.repository.IngrediantRepository;
import mg.itu.repository.PrixIngrediantRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/prix-ingrediant")
public class AdminPrixIngredientController {

    @Autowired
    private PrixIngrediantRepository prixIngrediantRepository;

    @Autowired
    private IngrediantRepository ingrediantRepository;

    @GetMapping
    public String afficherPrixIngrediants(@RequestParam(required = false) Long idIngrediant, Model model) {
        List<Ingrediant> Ingrediants = ingrediantRepository.findAll();
        List<PrixIngrediant> prixIngrediants;

        if (idIngrediant != null) {
            prixIngrediants = prixIngrediantRepository.findByIngrediantId(idIngrediant);
        } else {
            prixIngrediants = prixIngrediantRepository.findAllOrderByDateDesc();
        }

        model.addAttribute("ingrediants", Ingrediants);
        model.addAttribute("prixIngrediants", prixIngrediants);
        model.addAttribute("page", "admin/prix/prix-ingrediant");
        return "template/template";
    }

    @PostMapping("/ajouter")
    public String ajouterPrixIngrediant(@RequestParam Long idIngrediant, @RequestParam Double prix) {
        Ingrediant Ingrediant = ingrediantRepository.findById(idIngrediant).orElseThrow(() -> new IllegalArgumentException("Ingrediant non trouvé"));
        
        PrixIngrediant prixIngrediant = new PrixIngrediant();
        prixIngrediant.setIngrediant(Ingrediant);
        prixIngrediant.setPrix(prix);
        prixIngrediant.setDatePrix(LocalDateTime.now());

        prixIngrediantRepository.save(prixIngrediant);

        return "redirect:/admin/prix-ingrediant";
    }
}
