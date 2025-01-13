package mg.itu.model.categorie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DetailCategorieProduitRecette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCategorieProduitRecete", nullable = false)
    private CategorieProduitRecete categorieProduitRecete;

    @ManyToOne
    @JoinColumn(name = "idCategorieIngrediant", nullable = false)
    private Categorie categorieIngrediant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategorieProduitRecete getCategorieProduitRecete() {
        return categorieProduitRecete;
    }

    public void setCategorieProduitRecete(CategorieProduitRecete categorieProduitRecete) {
        this.categorieProduitRecete = categorieProduitRecete;
    }

    public Categorie getCategorieIngrediant() {
        return categorieIngrediant;
    }

    public void setCategorieIngrediant(Categorie categorieIngrediant) {
        this.categorieIngrediant = categorieIngrediant;
    }

    
}