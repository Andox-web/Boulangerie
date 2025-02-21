package mg.itu.controller.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.vente.CommissionVente;
import mg.itu.model.vente.Sexe;
import mg.itu.model.vente.Vendeur;
import mg.itu.repository.CommissionVenteRepository;
import mg.itu.repository.SexeRepository;
import mg.itu.repository.VendeurRepository;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/commission")
public class CommissionController {

    @Autowired
    private CommissionVenteRepository commissionVenteRepository;

    @Autowired
    private VendeurRepository vendeurRepository;
    
    @Autowired
    private SexeRepository sexeRepository;
    @GetMapping
    public String commission (@RequestParam(required = false)String dateStr,
                            @RequestParam(required = false) String dateDebutStr,
                            @RequestParam(required = false) String dateFinStr, Model model) {
        LocalDateTime date, dateDebut, dateFin, d;
        List<Vendeur> vendeurs = vendeurRepository.findAll();
        List<CommissionVente> commissionVentes = commissionVenteRepository.findAll();
        if (dateStr != null && !dateStr.isEmpty()) {
            date = d = LocalDateTime.parse(dateStr);
            commissionVentes = commissionVentes.stream().filter(c ->{
                    LocalDate dateC = c.getDateCommissionVente().toLocalDate();
                    LocalDate dateA = date.toLocalDate();
                    return dateC.isEqual(dateA);
                }).toList();
        }
        else if (dateDebutStr != null && !dateDebutStr.isEmpty() && dateFinStr != null && !dateFinStr.isEmpty()) {
            dateDebut = LocalDateTime.parse(dateDebutStr);
            dateFin = d = LocalDateTime.parse(dateFinStr);
            
            commissionVentes = commissionVentes.stream().filter(c -> {
                LocalDate dDebut = dateDebut.toLocalDate();
                LocalDate dFin = dateFin.toLocalDate();
                LocalDate dNow = c.getDateCommissionVente().toLocalDate(); 
                return dNow.isAfter(dDebut) && dNow.isBefore(dFin);
            }).toList();
        }
        else {
            d = LocalDateTime.now();
        }
        List<CommissionVente> commissionGroupes = new ArrayList<>();

        for (Vendeur vendeur : vendeurs) {
            CommissionVente commissionVente = new CommissionVente();
            double montant = 0.0;
            for (CommissionVente c : commissionVentes) {
                if (c.getVendeur().getId().equals(vendeur.getId())) {
                    montant += c.getMontant();
                }
            }
            commissionVente.setMontant(montant);
            commissionVente.setVendeur(vendeur);
            commissionVente.setDateCommissionVente(d);
            commissionGroupes.add(commissionVente);
        }
        double commissionHomme = 0;
        double commissionFemme = 0;
        for (CommissionVente commissionVente : commissionGroupes) {
            Sexe sexe = commissionVente.getVendeur().getSexe();
            if (sexe.getNom().equals("homme")) {
                commissionHomme+=commissionVente.getMontant();
            }else if (sexe.getNom().equals("femme")) {
                commissionFemme+=commissionVente.getMontant();
            }
        }
        List<Sexe> sexes = sexeRepository.findAll();
        Map<Sexe,Double> commissionParSexe =  new HashMap<>();
        for (Sexe sexe : sexes) {
            commissionParSexe.put(sexe, 0.0);
        }       
        for (CommissionVente commissionVente : commissionGroupes) {
            Sexe sexe = commissionVente.getVendeur().getSexe();
            commissionParSexe.put(sexe,commissionParSexe.get(sexe)+commissionVente.getMontant());
        }
        
        model.addAttribute("commissionSexes", commissionParSexe);
        model.addAttribute("commissionHomme",commissionHomme);
        model.addAttribute("commissionFemme",commissionFemme);
        model.addAttribute("commissionVentes", commissionGroupes);
        model.addAttribute("page", "admin/vente/commission-vente");
        return "template/template";
    }
}
