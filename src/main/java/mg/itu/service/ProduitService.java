package mg.itu.service;

import mg.itu.model.Produit;
import mg.itu.model.categorie.Categorie;
import mg.itu.model.categorie.ProduitCategorie;
import mg.itu.model.prix.PrixProduit;
import mg.itu.repository.CategorieRepository;
import mg.itu.repository.PrixProduitRepository;
import mg.itu.repository.ProduitCategorieRepository;
import mg.itu.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitCategorieRepository produitCategorieRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private PrixProduitRepository prixProduitRepository;

    public List<Produit> getAllProduits() {
        return addPrix(addCategorie(produitRepository.findAll()));
    }

    public Produit getProduitById(Long id) {
        Produit produit = produitRepository.findById(id).orElse(null);
        if (produit!=null) {
            produit.setCategories(produitRepository.findAllCategorie(produit.getId()));
            produit.setPrix(produitRepository.findPrixOptional(id).orElse(0.0));
        }
        return produit;
    }    
    
    public Produit getProduitById(Long id,LocalDateTime dateVente) {
        Produit produit = produitRepository.findById(id).orElse(null);
        if (produit!=null) {
            produit.setCategories(produitRepository.findAllCategorie(produit.getId()));
            produit.setPrix(produitRepository.findPrixOptional(id,dateVente).orElse(0.0));
        }
        return produit;
    }    


    public List<Produit> getProduitsFiltres(String nom, Integer categorie, Double prixMin, Double prixMax) {
        List<Produit> produits = null;
        if (nom != null && !nom.isEmpty() && categorie != null && prixMin != null && prixMax != null) {
            produits = produitRepository.findByNomAndCategorieAndPrixBetween(nom, categorie, prixMin, prixMax);
        } else if (nom != null && !nom.isEmpty() && categorie != null) {
            produits = produitRepository.findByNomAndIdCategorie(nom, categorie);
        } else if (nom != null && !nom.isEmpty() && prixMin != null && prixMax != null) {
            produits = produitRepository.findByNomAndPrixBetween(nom, prixMin, prixMax);
        } else if (categorie != null && prixMin != null && prixMax != null) {
            produits = produitRepository.findByCategorieAndPrixBetween(categorie, prixMin, prixMax);
        } else if (nom != null && !nom.isEmpty()) {
            produits = produitRepository.findByNom(nom);
        } else if (categorie != null) {
            produits = produitRepository.findByIdCategorie(categorie);
        } else if (prixMin != null && prixMax != null) {
            produits = produitRepository.findByPrixBetween(prixMin, prixMax);
        }
        if (produits == null) {
            produits = produitRepository.findAll();
        }

        addQuantite(addPrix(addCategorie(produits)));
        return produits;
    }

    public List<Produit> addCategorie(List<Produit> produits){
        for (Produit produit : produits) {
            produit.setPrix(produitRepository.findPrixOptional(produit.getId()).orElse(0.0));
        }
        return produits;
    }

    public List<Produit> addPrix(List<Produit> produits){
        for (Produit produit : produits) {
            produit.setCategories(produitRepository.findAllCategorie(produit.getId()));
        }
        return produits;   
    }
    public List<Produit> addQuantite(List<Produit> produits){
        for (Produit produit : produits) {
            produit.setQuantite(produitRepository.findQuantiteOptional(produit.getId()).orElse(0.0));
        }
        return produits;   
    }

    @Transactional
    public void addProduit(Long id, String nom, String description, List<Long> categories, LocalDateTime dateCreation) {
        Produit produit = new Produit();
        if (id != null) {
            produit = produitRepository.findById(id).orElse(produit);

            List<ProduitCategorie> produitCategories = new ArrayList<>(produit.getProduitCategories());

            produitCategorieRepository.deleteAll(produitCategories);

            produit.getProduitCategories().clear();
        }

        produit.setNom(nom);
        produit.setDescription(description);
        if (dateCreation==null) {
            dateCreation =LocalDateTime.now();
        }
        produit.setDateCreation(dateCreation);
        produitRepository.saveAndFlush(produit);
        
        List<Categorie> categories2 = categorieRepository.findAllById(categories);
        
        produit.setProduitCategoriesByCategorie(categories2);

        if (id==null) {
            PrixProduit prixProduit =  new PrixProduit();
            prixProduit.setDatePrix(dateCreation);
            prixProduit.setPrix(0.0);
            prixProduit.setProduit(produit);
            prixProduitRepository.save(prixProduit);            
        }

        produitCategorieRepository.saveAllAndFlush(produit.getProduitCategories());
    }


}
