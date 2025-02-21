package mg.itu.controller.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Produit;
import mg.itu.model.auth.Utilisateur;
import mg.itu.model.categorie.Categorie;
import mg.itu.model.parfum.Parfum;
import mg.itu.model.vente.Vendeur;
import mg.itu.model.vente.Vente;
import mg.itu.repository.CategorieRepository;
import mg.itu.repository.ParfumRepository;
import mg.itu.repository.UtilisateurRepository;
import mg.itu.repository.VendeurRepository;
import mg.itu.service.ProduitService;
import mg.itu.service.VenteService;
import mg.itu.util.DateUtil;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/vente")
public class AdminVenteControlleur {

    @Autowired
    private VenteService venteService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private ParfumRepository parfumRepository;

    @Autowired VendeurRepository vendeurRepository;

    @GetMapping
    public String afficherVentes(Model model, 
                                 @RequestParam(required = false) Long categorie, 
                                 @RequestParam(required = false) Long parfum) {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll().stream().filter(u -> u.getRole().getNom().equalsIgnoreCase("client")).toList();
        List<Produit> produits = produitService.getAllProduits();
        List<Categorie> categories = categorieRepository.findByTypeCategorie_Nom("produit");
        List<Parfum> parfums = parfumRepository.findAll();
        List<Vente> ventes = venteService.filtreVentes(categorie, parfum);
        List<Vendeur> vendeurs = vendeurRepository.findAll();
        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("categorie", categorie);
        model.addAttribute("parfum", parfum);
        model.addAttribute("produits", produits);
        model.addAttribute("categories", categories);
        model.addAttribute("parfums", parfums);
        model.addAttribute("ventes", ventes);
        model.addAttribute("vendeurs", vendeurs);
        model.addAttribute("page", "admin/vente/vente");
        return "template/template";
    }

    @PostMapping("/ajouter")
    public String ajouterVente(@RequestParam(required = false) Long utilisateur, 
                               @RequestParam(required = false) List<Long> produits, 
                               @RequestParam(required = false) String dateVenteStr, 
                               Long idVendeur,
                               HttpServletRequest request,
                               RedirectAttributes model) {
        try {
           
            List<Double> quantites = new ArrayList<>();
            if(produits == null) {
                throw new Exception("Veuillez sélectionner au moins un produit.");
            }
            for (Long produit : produits) {
                Double quantite;
                try {
                    quantite = Double.parseDouble(request.getParameter("quantite" + produit));
                } catch (NumberFormatException e) {
                    quantite = 0.0;
                } 
                
                if (quantite <= 0) {
                    throw new Exception("Les quantité doivent être supérieure à 0.");
                }
                quantites.add(quantite);
            }
            LocalDateTime dateVente = DateUtil.parse(dateVenteStr);
            venteService.ajouterVente(utilisateur, produits, quantites, dateVente, idVendeur);
            model.addFlashAttribute("success", "Vente ajoutée avec succès.");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erreur lors de l'ajout de la vente : " + e.getMessage());
        }
        return "redirect:/admin/vente";
    }

}
