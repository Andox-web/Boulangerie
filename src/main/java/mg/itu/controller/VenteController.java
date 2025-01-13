package mg.itu.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Produit;
import mg.itu.model.auth.Utilisateur;
import mg.itu.model.vente.Vente;
import mg.itu.service.ProduitService;
import mg.itu.service.VenteService;


@Controller
@RoleRequired(role = "client")
public class VenteController {

    @Autowired
    private VenteService venteService;

    @Autowired
    private ProduitService produitService;

    @GetMapping("/panier")
    public String getVente(Model model,HttpSession session){
        Vente vente = (Vente)session.getAttribute("vente"); 
        model.addAttribute("vente", vente);
        model.addAttribute("page","vente/vente");
        return "template/template";
    }

    @GetMapping("/ajouter-au-panier")
    public String getVente(@RequestParam(defaultValue = "/panier") String url,Long idProduit,Double quantite,Model model,HttpSession session){
        Produit produit = produitService.getProduitById(idProduit);
        if(produit!=null){
            Vente vente = (Vente)session.getAttribute("vente"); 
            if (vente==null) {
                Utilisateur utilisateur = (Utilisateur)session.getAttribute("utilisateur");
                vente =venteService.creerVente(utilisateur);
                session.setAttribute("vente", vente);
            }
            vente.addVente(produit,quantite);
        }
        return "redirect:"+url;
    }

    @GetMapping("/confirm")
    public String confirmVente(HttpSession session,RedirectAttributes redirectAttributes) {
        Vente Vente = (Vente)session.getAttribute("vente");
        try {
            Vente.setDateVente(LocalDateTime.now());
            venteService.confirmVente(Vente);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",e.getMessage());
            return "redirect:/panier";
        }
        session.setAttribute("Vente",null);
        redirectAttributes.addFlashAttribute("success","Vente confirmer");
        return "redirect:/"; 
    }

}
