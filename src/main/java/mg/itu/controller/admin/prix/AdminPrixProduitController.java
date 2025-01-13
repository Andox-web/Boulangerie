package mg.itu.controller.admin.prix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String afficherPrixProduits(@RequestParam(required = false) Long idProduit, Model model) {
        List<Produit> produits = produitRepository.findAll();
        List<PrixProduit> prixProduits;

        if (idProduit != null) {
            prixProduits = prixProduitRepository.findByProduitId(idProduit);
        } else {
            prixProduits = prixProduitRepository.findAllOrderByDateDesc();
        }

        model.addAttribute("produits", produits);
        model.addAttribute("prixProduits", prixProduits);
        model.addAttribute("page", "admin/prix/prix-produit");
        return "template/template";
    }

    @PostMapping("/ajouter")
    public String ajouterPrixProduit(@RequestParam Long idProduit, @RequestParam Double prix) {
        Produit produit = produitRepository.findById(idProduit).orElseThrow(() -> new IllegalArgumentException("Produit non trouvé"));
        
        PrixProduit prixProduit = new PrixProduit();
        prixProduit.setProduit(produit);
        prixProduit.setPrix(prix);
        prixProduit.setDatePrix(LocalDateTime.now());

        prixProduitRepository.save(prixProduit);

        return "redirect:/admin/prix-produit";
    }
}
