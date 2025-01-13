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
import mg.itu.model.Produit;
import mg.itu.model.stock.StockProduit;
import mg.itu.repository.ProduitRepository;
import mg.itu.repository.StockProduitRepository;

@Controller
@RoleRequired(role = "admin")
@RequestMapping("/admin/stock/stock-produit")
public class AdminStockProduitController {

    @Autowired
    private StockProduitRepository stockProduitRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public String afficherTransactions(
            @RequestParam(required = false) Long idProduit,
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            Model model) {
        
        List<Produit> produits = produitRepository.findAll();
        List<StockProduit> transactions;
        
        if (idProduit != null || dateDebut != null || dateFin != null) {
            transactions = stockProduitRepository.findByFilters(idProduit, dateDebut, dateFin);
        } else {
            transactions = stockProduitRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTransaction"));
        }
        
        model.addAttribute("produits", produits);
        model.addAttribute("transactions", transactions);
        model.addAttribute("page", "admin/stock/stockProduit");
        return "template/template";
    }


    @PostMapping("/ajouter")
    public String ajouterTransaction(
            @RequestParam Long idProduit,
            @RequestParam(required = false,defaultValue = "0") Double entree,
            @RequestParam(required = false,defaultValue = "0") Double sortie,
            @RequestParam String raison,
            Model model) {
        
        StockProduit transaction = new StockProduit();
        Produit produit = produitRepository.findById(idProduit).orElse(null);
        if (produit != null) {
            transaction.setProduit(produit);
            transaction.setEntree(entree);
            transaction.setSortie(sortie);
            transaction.setRaison(raison);
            transaction.setDateTransaction(LocalDate.now().atStartOfDay());
            stockProduitRepository.save(transaction);
        }

        return "redirect:/admin/stock/stock-produit";
    }

    @GetMapping("/supprimer")
    public String supprimerTransaction(@RequestParam Long id) {
        stockProduitRepository.deleteById(id);
        return "redirect:/admin/stock/stock-produit";
    }
}