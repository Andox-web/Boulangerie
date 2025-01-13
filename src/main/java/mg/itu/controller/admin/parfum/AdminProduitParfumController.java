package mg.itu.controller.admin.parfum;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Produit;
import mg.itu.model.parfum.Parfum;
import mg.itu.model.parfum.ProduitParfum;
import mg.itu.repository.ParfumRepository;
import mg.itu.repository.ProduitParfumRepository;
import mg.itu.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/produit-parfum")
public class AdminProduitParfumController {

    @Autowired
    private ProduitParfumRepository produitParfumRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ParfumRepository parfumRepository;

    @GetMapping
    public String afficherProduitParfums(Model model, @RequestParam(required = false) Long id) {
        List<ProduitParfum> produitParfums = produitParfumRepository.findAll();
        List<Produit> produits = produitRepository.findAll();
        List<Parfum> parfums = parfumRepository.findAll();
        model.addAttribute("produitParfums", produitParfums);
        model.addAttribute("produits", produits);
        model.addAttribute("parfums", parfums);

        if (id != null) {
            ProduitParfum produitParfum = produitParfumRepository.findById(id).orElse(null);
            model.addAttribute("produitParfum", produitParfum);
        }

        model.addAttribute("page", "admin/parfum/produit-parfum");
        return "template/template";
    }

    @PostMapping("/sauvegarder")
    public String sauvegarderProduitParfum(@RequestParam(required = false) Long id, 
                                           @RequestParam Long produit, 
                                           @RequestParam Long parfum, 
                                           RedirectAttributes model) {
        try {
            ProduitParfum produitParfum;
            if (id != null) {
                produitParfum = produitParfumRepository.findById(id).orElse(new ProduitParfum());
            } else {
                produitParfum = new ProduitParfum();
            }
            produitParfum.setProduit(produitRepository.findById(produit).orElse(null));
            produitParfum.setParfum(parfumRepository.findById(parfum).orElse(null));
            produitParfumRepository.save(produitParfum);
            model.addFlashAttribute("success", "Produit Parfum sauvegardé avec succès.");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erreur lors de la sauvegarde du Produit Parfum : " + e.getMessage());
        }
        return "redirect:/admin/produit-parfum";
    }

    @GetMapping("/supprimer")
    public String supprimerProduitParfum(@RequestParam Long id, RedirectAttributes model) {
        try {
            produitParfumRepository.deleteById(id);
            model.addFlashAttribute("success", "Produit Parfum supprimé avec succès.");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erreur lors de la suppression du Produit Parfum : " + e.getMessage());
        }
        return "redirect:/admin/produit-parfum";
    }
}
