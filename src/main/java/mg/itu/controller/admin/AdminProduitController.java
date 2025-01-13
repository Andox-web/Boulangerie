package mg.itu.controller.admin;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import mg.itu.repository.ProduitRepository;
import mg.itu.repository.CategorieRepository;
import mg.itu.service.ProduitService;
import mg.itu.util.DateUtil;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin")
public class AdminProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ProduitService produitService;


    @GetMapping("/produit")
    public String getCrudProduit(Long id, String nom,Integer categorie,Double prixMin,Double prixMax, Model model) {
        List<Produit> produits = null;
        List<Categorie> categories = categorieRepository.findByTypeCategorie_Nom("produit");
        model.addAttribute("categories", categories);

        produits = produitService.getProduitsFiltres(nom, categorie, prixMin, prixMax);
        produitService.addCategorie(produits);
        if (id != null) {
            Produit produit = produitRepository.findById(id).orElse(null);
            if (produit!=null) {
                produit.setCategories(produitRepository.findAllCategorie(produit.getId()));
                model.addAttribute("produit", produit);
            }
        }

        model.addAttribute("produits", produits);
        model.addAttribute("page", "admin/produit");
        return "template/template";  // Assurez-vous que le chemin correspond à votre structure
    }

    @PostMapping("/produit/sauvegarder")
    public String sauvegarderProduit(Long id, String nom, String description,@RequestParam("dateCreation") String dateCreationStr,Long[] categories, RedirectAttributes redirectAttributes) {
        List<Long> cList=Arrays.stream(categories)
                                    .collect(Collectors.toList());
        try {
            LocalDateTime dateCreation =  DateUtil.parse(dateCreationStr);
            
            produitService.addProduit(id, nom, description,cList,dateCreation);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'insertion du produit");
        }
        return "redirect:/admin/produit";
    }
}
