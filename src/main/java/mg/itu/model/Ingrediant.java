package mg.itu.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import mg.itu.model.categorie.IngrediantCategorie;

@Entity
public class Ingrediant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String unite;

    private String description;

    @OneToMany(mappedBy = "ingrediant",fetch = FetchType.EAGER)
    private List<IngrediantCategorie> ingrediantCategories;
    @Transient
    private Double prix = 0.0;

    @Transient
    private Double quantite= 0.0;
    
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

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public List<IngrediantCategorie> getIngrediantCategories() {
        return ingrediantCategories;
    }

    public void setIngrediantCategories(List<IngrediantCategorie> ingrediantCategories) {
        this.ingrediantCategories = ingrediantCategories;
    }

    

    
}