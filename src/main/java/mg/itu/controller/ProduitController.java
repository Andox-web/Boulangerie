package mg.itu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mg.itu.model.Produit;
import mg.itu.service.ProduitService;

@Controller
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/produit")
    public String getDetailProduit(Long id,Model model){
        Produit produit = produitService.getProduitById(id);
        if (produit == null) {
            return "redirect:/";
        }
        model.addAttribute("produit", produit);
        model.addAttribute("page", "produit-detail");
        return "template/template";
    } 
}
