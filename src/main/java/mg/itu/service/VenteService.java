package mg.itu.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import mg.itu.model.Produit;
import mg.itu.model.auth.Utilisateur;
import mg.itu.model.stock.StockProduit;
import mg.itu.model.vente.DetailVente;
import mg.itu.model.vente.Vente;
import mg.itu.repository.DetailVenteRepository;
import mg.itu.repository.ProduitRepository;
import mg.itu.repository.StockProduitRepository;
import mg.itu.repository.UtilisateurRepository;
import mg.itu.repository.VenteRepository;

@Service
public class VenteService {
    @Autowired
    private VenteRepository venteRepository;
    
    @Autowired
    private DetailVenteRepository detailVenteRepository;

    @Autowired
    private StockProduitRepository stockProduitRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ProduitService produitsService;

    @Autowired
    private ProduitRepository produitRepository;

    public Vente creerVente(Utilisateur utilisateur) {
        Vente vente = new Vente();
        vente.setUtilisateur(utilisateur);
        vente.setTotal(0.0);
        return vente;
    }
    public Vente addVente(Produit produit,Double quantite, Vente vente) {
        vente.addVente(produit, quantite);
        return vente;
    }
    @Transactional
    public void confirmVente(Vente vente){
        vente.filtreDetailVente();	
        if (vente.getDetailVentes() == null || vente.getDetailVentes().isEmpty() ) {
            throw new RuntimeException("""
                La vente ne contient aucun produit
                """); 
        }
        List<StockProduit> stockProduits = new ArrayList<>();
        StringBuilder insufficientProducts = new StringBuilder();

        for (DetailVente detailVente : vente.getDetailVentes()) {
            Double quantiteStock = produitRepository.findQuantiteOptional(detailVente.getProduit().getId()).orElse(0.0);    
            if (quantiteStock < detailVente.getQuantite()) {
                insufficientProducts.append(detailVente.getProduit().getNom())
                                    .append(" (manque: ")
                                    .append(detailVente.getQuantite() - quantiteStock)
                                    .append("), ");
            } else {
                StockProduit stockProduit = new StockProduit();
                stockProduit.setProduit(detailVente.getProduit());
                stockProduit.setEntree(0.0);
                stockProduit.setSortie(detailVente.getQuantite());
                stockProduit.setDateTransaction(LocalDateTime.now());
                stockProduits.add(stockProduit);
            }
        }

        if (insufficientProducts.length() > 0) {
            throw new RuntimeException("Produits insuffisants: " + insufficientProducts.toString());
        }

        venteRepository.saveAndFlush(vente);
        detailVenteRepository.saveAll(vente.getDetailVentes());
        stockProduitRepository.saveAll(stockProduits);
    }
    public List<Vente> findAll() {
        return venteRepository.findAll();
    }
    public void ajouterVente(Long utilisateur, List<Long> produits, List<Double> quantites, LocalDateTime dateVente) {
        Utilisateur user=null;
        if(utilisateur!=null){
            user = utilisateurRepository.findById(utilisateur).orElse(null);
            if (user == null) {
                throw new RuntimeException("""
                    L'utilisateur n'existe pas
                    """);
            }
        }
        if (dateVente == null) {
            dateVente = LocalDateTime.now();
        }
        Vente vente = creerVente(user);
        for (int i = 0; i < produits.size(); i++) {
            Produit produit = produitsService.getProduitById(produits.get(i),dateVente);
            if (produit==null) {
                throw new RuntimeException("""
                    Le produit id="""+produits.get(i)+"""
                     n'existe pas
                    """);
                
            }
            addVente(produit, quantites.get(i).doubleValue(), vente);
        }
        
        vente.setDateVente(dateVente);
        confirmVente(vente);        
    }
    public List<Vente> filtreVentes(Long categorie, Long parfum) {
        List<Vente> ventes = venteRepository.findAll();
        List<Vente> filteredVentes = new ArrayList<>();

        for (Vente vente : ventes) {
            boolean matchesCategorie = (categorie == null);
            boolean matchesParfum = (parfum == null);

            for (DetailVente detail : vente.getDetailVentes()) {
                if (!matchesCategorie) {
                    matchesCategorie = detail.getProduit().getProduitCategories().stream()
                        .anyMatch(cat -> cat.getCategorie().getId().equals(categorie));
                }
                if (!matchesParfum) {
                    if (parfum == 0L) {// 0 pour les produits sans parfum nature
                        matchesParfum = detail.getProduit().getProduitParfums().isEmpty();
                    } else {
                        matchesParfum = detail.getProduit().getProduitParfums().stream()
                            .anyMatch(parf -> parf.getParfum().getId().equals(parfum));
                    }
                }
                if (matchesCategorie && matchesParfum) {
                    filteredVentes.add(vente);
                    break;
                }
            }
        }
        return filteredVentes;
    }
    public List<Vente> findAllByDateRange(String dateDebut, String dateFin) {
        return venteRepository.findAllByDateRange(dateDebut, dateFin);
    }
}
