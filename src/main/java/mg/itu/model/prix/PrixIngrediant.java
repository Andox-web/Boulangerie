package mg.itu.model.prix;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import mg.itu.model.Ingrediant;

@Entity
public class PrixIngrediant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idIngrediant", nullable = false)
    private Ingrediant ingrediant;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = false)
    private LocalDateTime datePrix;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingrediant getIngrediant() {
        return ingrediant;
    }

    public void setIngrediant(Ingrediant ingrediant) {
        this.ingrediant = ingrediant;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public LocalDateTime getDatePrix() {
        return datePrix;
    }

    public void setDatePrix(LocalDateTime datePrix) {
        this.datePrix = datePrix;
    }

}
