package mg.itu.model.fabrication;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import mg.itu.model.Produit;

@Entity
public class Recette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idProduit", nullable = false)
    private Produit produit;

    @Column(nullable = false)
    private Double dureePreparation;

    private String description;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "recette", fetch = FetchType.EAGER)
    private List<DetailRecette> detailRecettes;

    public List<DetailRecette> getDetailRecettes() {
        return detailRecettes;
    }

    public void setDetailRecettes(List<DetailRecette> detailRecettes) {
        this.detailRecettes = detailRecettes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Double getDureePreparation() {
        return dureePreparation;
    }

    public void setDureePreparation(Double dureePreparation) {
        this.dureePreparation = dureePreparation;
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
}