package mg.itu.model.categorie;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CategorieProduitRecete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCategorieProduit", nullable = false)
    private Categorie categorieProduit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categorie getCategorieProduit() {
        return categorieProduit;
    }

    public void setCategorieProduit(Categorie categorieProduit) {
        this.categorieProduit = categorieProduit;
    }


}
