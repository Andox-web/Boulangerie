package mg.itu.model.fabrication;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import mg.itu.model.Produit;

@Entity
public class Fabrication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idProduit", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "idRecette", nullable = false)
    private Recette recette;

    @Column(nullable = false)
    private Double quantite;

    @Column(nullable = false)
    private LocalDateTime dateFabrication;
    
    @Transient
    private LocalDateTime dateFin;
    
    @Transient
    private boolean isFinished ;

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

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public LocalDateTime getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(LocalDateTime dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public LocalDateTime getDateFin() {
        Double duree=quantite*recette.getDureePreparation();
        return dateFabrication.plusSeconds(duree.longValue());
    }

    public boolean isFinished() {
        return LocalDateTime.now().isAfter(getDateFin());
    }
   
}