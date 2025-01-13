package mg.itu.controller.admin.stock;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.model.Ingrediant;
import mg.itu.model.stock.StockIngrediant;
import mg.itu.repository.IngrediantRepository;
import mg.itu.repository.StockIngrediantRepository;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/stock/stock-ingrediant")
public class AdminStockIngrediantController {
    
    @Autowired
    private StockIngrediantRepository stockIngrediantRepository;

    @Autowired 
    private IngrediantRepository IngrediantRepository;

    @GetMapping
    public String afficherTransactions(
            @RequestParam(required = false) Long idIngrediant,
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            Model model) {
        
        List<Ingrediant> Ingrediants = IngrediantRepository.findAll();
        List<StockIngrediant> transactions;
        
        if (idIngrediant != null || dateDebut != null || dateFin != null) {
            transactions = stockIngrediantRepository.findByFilters(idIngrediant, dateDebut, dateFin);
        } else {
            transactions = stockIngrediantRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTransaction"));
        }
        
        model.addAttribute("ingredients", Ingrediants);
        model.addAttribute("transactions", transactions);
        model.addAttribute("page", "admin/stock/stockIngrediant");
        return "template/template";
    }


    @PostMapping("/ajouter")
    public String ajouterTransaction(
            @RequestParam Long idIngrediant,
            @RequestParam(required = false,defaultValue = "0" ) Double entree,
            @RequestParam(required = false,defaultValue = "0") Double sortie,
            @RequestParam String raison,
            Model model) {
        
        StockIngrediant transaction = new StockIngrediant();
        Ingrediant Ingrediant = IngrediantRepository.findById(idIngrediant).orElse(null);
        if (Ingrediant != null) {
            transaction.setIngrediant(Ingrediant);
            transaction.setEntree(entree);
            transaction.setSortie(sortie);
            transaction.setRaison(raison);
            transaction.setDateTransaction(LocalDate.now().atStartOfDay());
            stockIngrediantRepository.save(transaction);
        }

        return "redirect:/admin/stock/stock-ingrediant";
    }

    @GetMapping("/supprimer")
    public String supprimerTransaction(@RequestParam Long id) {
        stockIngrediantRepository.deleteById(id);
        return "redirect:/admin/stock/stock-ingrediant";
    }
}
