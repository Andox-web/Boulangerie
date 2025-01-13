package mg.itu.model.vente;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import mg.itu.model.auth.Utilisateur;

@Entity
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idUtilisateur", nullable = true)
    private Utilisateur utilisateur;

    @Column(nullable = false)
    private Double total = 0.0;

    @Column(nullable = false)
    private LocalDateTime dateVente;
    
    @OneToMany(mappedBy = "vente",fetch = FetchType.EAGER)
    private List<DetailVente> detailVentes=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Vente addVente(Produit produit,Double quantite){
        
        DetailVente dc = getDetailVente(produit);
        if (total!=0) {
            total-=dc.getQuantite()*produit.getPrix();
        }
        if (quantite<=0) {
            detailVentes.remove(dc);
            return this; 
        }

        setTotal(total+quantite*produit.getPrix());
        dc.setQuantite(quantite);
        dc.setTotal(quantite*produit.getPrix());

        return this;
    }

    private DetailVente getDetailVente(Produit produit){
        for(DetailVente dc : this.detailVentes){
            if(dc.getProduit().getId().equals(produit.getId())){
                return dc;
            }
        }
        DetailVente detailVente = new DetailVente();
        detailVente.setVente(this);
        detailVente.setProduit(produit);
        detailVente.setQuantite(0.0);
        detailVente.setTotal(0.0);
        detailVentes.add(detailVente);
        return detailVente; 
    }

    public void filtreDetailVente(){
        List<DetailVente> detailVentes = new ArrayList<>();
        for (DetailVente detailVente : this.detailVentes) {
            if (detailVente.getQuantite()>0) {
                detailVentes.add(detailVente);
            }
        }
        this.detailVentes=detailVentes;
    }
    public List<DetailVente> getDetailVentes() {
        return detailVentes;
    }

    public void setDetailVentes(List<DetailVente> detailVentes) {
        this.detailVentes = detailVentes;
    }

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }

}