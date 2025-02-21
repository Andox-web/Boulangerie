package mg.itu.controller.admin;

import java.time.LocalDate;
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

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Produit;
import mg.itu.model.Recommendation;
import mg.itu.repository.ProduitRepository;
import mg.itu.repository.RecommendationRepository;
import mg.itu.service.RecommendationService;
import mg.itu.util.DateUtil;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/recommendation")
public class RecommendationController {
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired 
    private RecommendationService recommendationService;

    @GetMapping
    public String getRecommendation(@RequestParam(required = false) Integer annee, String debutStr, String finStr, Model model) {
        LocalDateTime debut = DateUtil.parse(debutStr);
        LocalDateTime fin = DateUtil.parse(finStr);
        if (annee!=null) {
            debut = LocalDate.parse(annee+"-01-01").atStartOfDay();
            fin = LocalDate.parse(annee+"-12-31").atStartOfDay();
        }
        List<Recommendation> recommendations = recommendationService.getRecommendation(debut, fin);
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("page", "admin/recommendation");
        model.addAttribute("produits", produitRepository.findAll());
        return "template/template";
    }

    @PostMapping("/insert")
    public String insertRecommendation(Long idProduit, String debutStr, String finStr, RedirectAttributes redirectAttributes) {
        try {
            recommendationService.insertRecommendation(idProduit, debutStr, finStr);
            redirectAttributes.addFlashAttribute("success", "The recommendation was insert");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/recommendation";
    }
}
