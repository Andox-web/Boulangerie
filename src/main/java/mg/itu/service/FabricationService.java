package mg.itu.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mg.itu.model.Ingrediant;
import mg.itu.model.Produit;
import mg.itu.model.fabrication.DetailRecette;
import mg.itu.model.fabrication.Fabrication;
import mg.itu.model.fabrication.Recette;
import mg.itu.model.stock.StockIngrediant;
import mg.itu.model.stock.StockProduit;
import mg.itu.repository.FabricationRepository;
import mg.itu.repository.IngrediantRepository;
import mg.itu.repository.ProduitRepository;
import mg.itu.repository.RecetteRepository;
import mg.itu.repository.StockIngrediantRepository;
import mg.itu.repository.StockProduitRepository;

@Service
public class FabricationService {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private FabricationRepository fabricationRepository;


    @Autowired
    private RecetteRepository  recetteRepository;

    @Autowired
    private IngrediantRepository ingrediantRepository;

    @Autowired
    private StockProduitRepository stockProduitRepository;

    @Autowired
    private StockIngrediantRepository stockIngrediantRepository;

    public List<Fabrication> getFabrications(Long idCategorie, Long idIngrediant) {
        List<Produit> produits2 = new ArrayList<>();
        if (idCategorie != null) {
            produits2 = produitService.getProduitsFiltres(null, idCategorie.intValue(), null, null);
        }
        List<Fabrication> transactions = new ArrayList<>();
        if (idCategorie != null || idIngrediant != null) {
            for (Produit produit : produits2) {
                if (idCategorie != null && idIngrediant != null) {
                    transactions.addAll(fabricationRepository.findFabricationByProduitAndIngrediant(produit.getId(), idIngrediant));
                } else if (idCategorie != null) {
                    transactions.addAll(fabricationRepository.findFabricationByProduit(produit.getId()));
                }
            }
            if (produits2.isEmpty()) {
                transactions = fabricationRepository.findFabricationByIngrediant(idIngrediant);
            }
        } else {
            transactions = fabricationRepository.findAll(Sort.by(Sort.Direction.DESC, "dateFabrication"));
        }
        return transactions;
    }

    @Transactional
    public void saveFabrication(Long idProduit , Double quantite) {
        Fabrication transaction = new Fabrication();
        Produit produit = produitRepository.findById(idProduit).orElse(null);
        if (produit != null) {
            Recette recette = recetteRepository.findByProduit_IdOrderBydateCreationDesc(idProduit).orElse(null);

            if (recette != null) {
                transaction.setProduit(produit);
                transaction.setRecette(recette);
                transaction.setQuantite(quantite);
                transaction.setDateFabrication(LocalDateTime.now());
                List<DetailRecette> details = recette.getDetailRecettes();
                List<StockIngrediant> stockIngrediants = new ArrayList<>();
                for (DetailRecette dRecette : details) {
                    Ingrediant ingrediant=dRecette.getIngrediant();
                    ingrediant.setQuantite(ingrediantRepository.findQuantiteOptional(ingrediant.getId()).orElse(0.0));
                    if (ingrediant.getQuantite() < dRecette.getQuantite()*quantite) {
                        throw new RuntimeException("Stock insuffisant pour "+ingrediant.getNom());
                    } 
                    StockIngrediant stockIngrediant = new StockIngrediant();
                    stockIngrediant.setIngrediant(ingrediant);
                    stockIngrediant.setDateTransaction(transaction.getDateFin());
                    stockIngrediant.setSortie(dRecette.getQuantite()*quantite);
                    stockIngrediant.setEntree(0.0);
                    stockIngrediant.setRaison("fabrication "+produit.getId()+" quantite: "+quantite+" date:"+transaction.getDateFabrication());
                    stockIngrediants.add(stockIngrediant);
                }
                StockProduit stockProduit=new StockProduit();
                stockProduit.setProduit(produit);
                stockProduit.setDateTransaction(transaction.getDateFin());
                stockProduit.setSortie(quantite);
                stockProduit.setEntree(0.0);
                stockProduit.setRaison("fabrication "+produit.getId()+" quantite: "+quantite+" date:"+transaction.getDateFabrication());
                stockProduitRepository.save(stockProduit);
                stockIngrediantRepository.saveAll(stockIngrediants);
                fabricationRepository.save(transaction);    
            }else{
                throw new RuntimeException("Pas de recette pour ce produit");
            }
        }else{
            throw new RuntimeException("Produit introuvable");
        }
    }
}