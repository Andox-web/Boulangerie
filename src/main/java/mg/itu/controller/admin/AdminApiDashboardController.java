package mg.itu.controller.admin;

import mg.itu.annotation.auth.RoleRequired;
import mg.itu.repository.DetailVenteRepository;
import mg.itu.repository.StockIngrediantRepository;
import mg.itu.repository.StockProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RoleRequired(role = "admin")
@RequestMapping("/dashboard")
public class AdminApiDashboardController {

    @Autowired
    private DetailVenteRepository detailVenteRepository;

    @Autowired
    private StockProduitRepository stockProduitRepository;

    @Autowired
    private StockIngrediantRepository stockIngrediantRepository;

    @GetMapping("/sales")
    public Map<String, Map<String, List<Double>>> getSalesData(
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin) {

        // Valeurs par défaut pour les dates
        String defaultDateDebut = "1970-01-01";
        String defaultDateFin = LocalDateTime.now().toString();

        // Utiliser les valeurs par défaut si nécessaire
        dateDebut = (dateDebut == null || dateDebut.isEmpty()) ? defaultDateDebut : dateDebut;
        dateFin = (dateFin == null || dateFin.isEmpty()) ? defaultDateFin : dateFin;

        return detailVenteRepository.getSalesData(dateDebut, dateFin);
    }

    @GetMapping("/total-sales")
    public Map<String, Map<String, List<Double>>> getTotalSalesData(
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin) {

        // Valeurs par défaut pour les dates
        String defaultDateDebut = "1970-01-01";
        String defaultDateFin = LocalDateTime.now().toString();

        // Utiliser les valeurs par défaut si nécessaire
        dateDebut = (dateDebut == null || dateDebut.isEmpty()) ? defaultDateDebut : dateDebut;
        dateFin = (dateFin == null || dateFin.isEmpty()) ? defaultDateFin : dateFin;

        return detailVenteRepository.getTotalSalesData(dateDebut, dateFin);
    }

    @GetMapping("/most-sold-products")
    public List<Object[]> getMostSoldProducts(@RequestParam(required = false, defaultValue = "10") Integer limit) {
        return detailVenteRepository.findMostSoldProducts(limit);
    }


    @GetMapping("/product-quantities")
    public Map<String, Double> getProductQuantities() {
        return stockProduitRepository.getProductQuantities();
    }

    @GetMapping("/ingredient-quantities")
    public Map<String, Double> getIngrediantQuantities() {
        return stockIngrediantRepository.getIngrediantQuantities();
    }
}
