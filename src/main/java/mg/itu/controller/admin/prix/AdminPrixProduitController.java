package mg.itu.controller.admin.prix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Produit;
import mg.itu.model.prix.PrixProduit;
import mg.itu.repository.PrixProduitRepository;
import mg.itu.repository.ProduitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/prix-produit")
public class AdminPrixProduitController {

    @Autowired
    private PrixProduitRepository prixProduitRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public String afficherPrixProduits(@RequestParam(required = false) Long idProduit, 
                                       @RequestParam(required = false) String dateDebut, 
                                       @RequestParam(required = false) String dateFin, 
                                       Model model) {
        List<Produit> produits = produitRepository.findAll();
        List<PrixProduit> prixProduits;

        if (idProduit != null) {
            prixProduits = prixProduitRepository.findByProduitId(idProduit);
        } else {
            prixProduits = prixProduitRepository.findAllOrderByDateDesc();
        }

        if (dateDebut != null && !dateDebut.isEmpty() && dateFin != null && !dateFin.isEmpty()) {
            LocalDateTime debut = LocalDateTime.parse(dateDebut);
            LocalDateTime fin = LocalDateTime.parse(dateFin);
            prixProduits = prixProduits.stream()
                .filter(pp -> !pp.getDatePrix().isBefore(debut) && !pp.getDatePrix().isAfter(fin))
                .toList();
        }

        model.addAttribute("produits", produits);
        model.addAttribute("prixProduits", prixProduits);
        model.addAttribute("page", "admin/prix/prix-produit");
        return "template/template";
    }

    @PostMapping("/ajouter")
    public String ajouterPrixProduit(@RequestParam Long idProduit, 
                                     @RequestParam Double prix, 
                                     @RequestParam(required = false) String datePrixStr,
                                     RedirectAttributes redirectAttributes) {
        try {
            Produit produit = produitRepository.findById(idProduit).orElseThrow(() -> new IllegalArgumentException("Produit non trouvé"));
            
            PrixProduit prixProduit = new PrixProduit();
            prixProduit.setProduit(produit);
            prixProduit.setPrix(prix);
            if (datePrixStr != null) {
                prixProduit.setDatePrix(LocalDateTime.parse(datePrixStr));
            } else {
                prixProduit.setDatePrix(LocalDateTime.now());
            }

            prixProduitRepository.save(prixProduit);

            redirectAttributes.addFlashAttribute("message", "Prix produit ajouté avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'ajout du prix produit: " + e.getMessage());
        }

        return "redirect:/admin/prix-produit";
    }
}
