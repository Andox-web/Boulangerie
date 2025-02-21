package mg.itu.model.vente;

import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
public class CommissionVente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;

    @ManyToOne
    @JoinColumn(name = "idVendeur")
    private Vendeur vendeur;

    private LocalDateTime dateCommissionVente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Vendeur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Vendeur vendeur) {
        this.vendeur = vendeur;
    }

    public LocalDateTime getDateCommissionVente() {
        return dateCommissionVente;
    }

    public void setDateCommissionVente(LocalDateTime dateCommissionVente) {
        this.dateCommissionVente = dateCommissionVente;
    }

    
}
