package mg.itu.controller.admin.fabrication;

import jakarta.servlet.http.HttpServletRequest;
import mg.itu.model.Produit;
import mg.itu.model.fabrication.Recette;
import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Ingrediant;
import mg.itu.model.fabrication.DetailRecette;
import mg.itu.repository.ProduitRepository;
import mg.itu.repository.RecetteRepository;
import mg.itu.repository.IngrediantRepository;
import mg.itu.repository.DetailRecetteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/fabrication/recette")
public class AdminRecetteController {

    @Autowired
    private RecetteRepository recetteRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private IngrediantRepository ingrediantRepository;
    @Autowired
    private DetailRecetteRepository detailRecetteRepository;

    @GetMapping("/liste")
    public String listeRecettes(Model model) {
        List<Recette> recettes = recetteRepository.findAll();
        model.addAttribute("recettes", recettes);
        model.addAttribute("page", "admin/fabrication/liste-recette");
        return "template/template";
    }

    @GetMapping("/ajout")
    public String afficheFormulaireAjout(Model model) {
        List<Produit> produits = produitRepository.findAll();
        List<Ingrediant> ingredients = ingrediantRepository.findAll();
        model.addAttribute("produits", produits);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("page", "admin/fabrication/ajout-recette");
        return "template/template";
    }

    @PostMapping("/ajout")
    public String ajouterRecette(HttpServletRequest request) {
        Recette recette = new Recette();
        Long produitId = Long.parseLong(request.getParameter("produit"));
        recette.setProduit(produitRepository.findById(produitId).orElse(null));
        recette.setDureePreparation(Double.parseDouble(request.getParameter("dureePreparation")));
        recette.setDescription(request.getParameter("description"));
        recette.setDateCreation(LocalDateTime.now());

        Recette savedRecette = recetteRepository.save(recette);

        List<DetailRecette> detailRecettes = new ArrayList<>();
        ingrediantRepository.findAll().forEach(ingredient -> {
            String quantiteStr = request.getParameter("quantite" + ingredient.getId());
            if (quantiteStr != null && !quantiteStr.isEmpty()) {
                DetailRecette detail = new DetailRecette();
                detail.setRecette(savedRecette);
                detail.setIngrediant(ingredient);
                detail.setQuantite(Double.parseDouble(quantiteStr));
                detailRecettes.add(detail);
            }
        });

        detailRecetteRepository.saveAll(detailRecettes);
        return "redirect:/admin/fabrication/recette/liste";
    }

    @GetMapping("/info")
    public String detailsRecette(@RequestParam("id") Long id, Model model) {
        Recette recette = recetteRepository.findById(id).orElse(null);
        if (recette == null) {
            return "redirect:/admin/fabrication/recette/liste";
        }
        model.addAttribute("recette", recette);
        model.addAttribute("page", "admin/fabrication/info-recette");
        return "template/template";
    }
}
