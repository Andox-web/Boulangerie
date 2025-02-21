package mg.itu.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.auth.Utilisateur;
import mg.itu.model.vente.Vente;
import mg.itu.repository.VenteRepository;
import mg.itu.util.DateUtil;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/vente/filtre")
public class VenteClientController {
    @Autowired VenteRepository venteRepository;

    @GetMapping
    public String filter(String dateStr, Model model) {
        List<Vente> ventes;
        if (dateStr == null||dateStr.isEmpty()) {
            ventes = venteRepository.findAll();
        }
        else {
            LocalDateTime date = LocalDate.parse(dateStr).atStartOfDay();
            
            ventes = venteRepository.findAll().stream()
            .filter(v -> v.getDateVente().getYear() == date.getYear() 
            && v.getDateVente().getMonthValue() == date.getMonthValue() 
            && v.getDateVente().getDayOfYear() == date.getDayOfYear() 
            && v.getUtilisateur()!=null)
            .toList();
        }
        List<Utilisateur> utilisateurs = ventes.stream().map(Vente::getUtilisateur).distinct().filter(u -> u !=null).toList();
        ventes =  ventes.stream().sorted((r1, r2) -> r2.getDateVente().compareTo(r1.getDateVente())).toList();
        model.addAttribute("ventes", ventes);
        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("date", dateStr == null ? LocalDate.now() : DateUtil.parse(dateStr));
        model.addAttribute("page", "admin/vente/filtre");
       return "template/template";
    }
}
