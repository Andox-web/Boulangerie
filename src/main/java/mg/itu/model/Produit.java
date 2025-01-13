package mg.itu.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import mg.itu.model.categorie.Categorie;
import mg.itu.model.categorie.ProduitCategorie;
import mg.itu.model.parfum.ProduitParfum;

@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String description;

    @Column(nullable = false,insertable = false)
    private LocalDateTime dateCreation;

    @Transient
    private Double prix = 0.0;

    @Transient
    private Double quantite= 0.0;
    
    @OneToMany(mappedBy = "produit" , fetch = FetchType.EAGER)
    List<ProduitCategorie> produitCategories = new ArrayList<>();

    @OneToMany(mappedBy = "produit" , fetch = FetchType.EAGER)
    List<ProduitParfum> produitParfums;
    @Transient
    private List<Categorie> categories;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public List<ProduitCategorie> getProduitCategories() {
        return produitCategories;
    }

    public void setProduitCategoriesByCategorie(List<Categorie> categories) {
        this.produitCategories = new ArrayList<>();
        for (Categorie categorie : categories) {   
            ProduitCategorie produitCategorie = new ProduitCategorie();
            produitCategorie.setCategorie(categorie);
            produitCategorie.setProduit(this);
            this.produitCategories.add(produitCategorie);
        }
    }

    public void setProduitCategories(List<ProduitCategorie> produitCategories) {
        this.produitCategories = produitCategories;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public List<ProduitParfum> getProduitParfums() {
        return produitParfums;
    }

    public void setProduitParfums(List<ProduitParfum> produitParfums) {
        this.produitParfums = produitParfums;
    }   
    
}