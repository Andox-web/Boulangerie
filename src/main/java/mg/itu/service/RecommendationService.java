package mg.itu.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.transaction.Transactional;
import mg.itu.model.Produit;
import mg.itu.model.Recommendation;
import mg.itu.repository.ProduitRepository;
import mg.itu.repository.RecommendationRepository;
import mg.itu.util.DateUtil;

@Service
public class RecommendationService {
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private ProduitRepository produitRepository;

    public List<Recommendation> getRecommendation(LocalDateTime debut, LocalDateTime fin) {
        final LocalDateTime fdebut = debut == null ? LocalDate.parse("1970-01-01").atStartOfDay() : debut;
        System.out.println(debut);
        final LocalDateTime ffin = fin == null ? LocalDateTime.now() : fin;
        System.out.println(fin);
        return recommendationRepository.findAll().stream()
            .filter(r -> (fdebut.isBefore(r.getDateDebut()) && ffin.isAfter(r.getDateDebut())) || 
                        (fdebut.isBefore(r.getDateFin()) && ffin.isAfter(r.getDateFin())))
            .sorted((r1, r2) -> r2.getDateDebut().compareTo(r1.getDateDebut()))
            .toList();
    }

    @Transactional
    public void insertRecommendation(Long idProduit, String dateDebutStr, String dateFinStr) {
        Produit produit = produitRepository.findById(idProduit).orElseThrow(() -> new RuntimeException("Product not found"));
        LocalDateTime dateDebut;
        LocalDateTime dateFin;
        try {
            dateDebut = LocalDateTime.parse(dateDebutStr);
            dateFin = LocalDateTime.parse(dateFinStr);
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException("les dates doivent etre valide");
        }
        if (dateDebut.isAfter(dateFin)) {
            throw new RuntimeException("Start date must be after End date");
        }
        Recommendation recommendation = new Recommendation();
        recommendation.setProduit(produit);
        recommendation.setDateDebut(dateDebut);
        recommendation.setDateFin(dateFin);
        recommendationRepository.save(recommendation);
    }
}
