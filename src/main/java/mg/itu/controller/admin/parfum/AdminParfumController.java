package mg.itu.controller.admin.parfum;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.parfum.Parfum;
import mg.itu.repository.ParfumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/parfum")
public class AdminParfumController {

    @Autowired
    private ParfumRepository parfumRepository;

    @GetMapping
    public String afficherParfums(Model model, @RequestParam(required = false) Long id) {
        List<Parfum> parfums = parfumRepository.findAll();
        model.addAttribute("parfums", parfums);

        if (id != null) {
            Parfum parfum = parfumRepository.findById(id).orElse(null);
            model.addAttribute("parfum", parfum);
        }

        model.addAttribute("page", "admin/parfum/parfum");
        return "template/template";
    }

    @PostMapping("/sauvegarder")
    public String sauvegarderParfum(@RequestParam(required = false) Long id, 
                                    @RequestParam String nom, 
                                    @RequestParam String description, 
                                    RedirectAttributes model) {
        try {
            Parfum parfum;
            if (id != null) {
                parfum = parfumRepository.findById(id).orElse(new Parfum());
            } else {
                parfum = new Parfum();
            }
            parfum.setNom(nom);
            parfum.setDescription(description);
            parfumRepository.save(parfum);
            model.addFlashAttribute("success", "Parfum sauvegardé avec succès.");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erreur lors de la sauvegarde du parfum : " + e.getMessage());
        }
        return "redirect:/admin/parfum";
    }

    @GetMapping("/supprimer")
    public String supprimerParfum(@RequestParam Long id, RedirectAttributes model) {
        try {
            parfumRepository.deleteById(id);
            model.addFlashAttribute("success", "Parfum supprimé avec succès.");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erreur lors de la suppression du parfum : " + e.getMessage());
        }
        return "redirect:/admin/parfum";
    }
}
