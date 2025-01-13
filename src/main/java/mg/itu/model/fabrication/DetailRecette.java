package mg.itu.model.fabrication;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import mg.itu.model.Ingrediant;

@Entity
public class DetailRecette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idRecette", nullable = false)
    private Recette recette;

    @ManyToOne
    @JoinColumn(name = "idIngrediant", nullable = false)
    private Ingrediant ingrediant;

    @Column(nullable = false)
    private Double quantite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public Ingrediant getIngrediant() {
        return ingrediant;
    }

    public void setIngrediant(Ingrediant ingrediant) {
        this.ingrediant = ingrediant;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    
}