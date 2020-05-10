/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import util.enumeration.OrderStatusEnum;

/**
 *
 * @author Kaijing
 */
@Entity
public class ArtworkOrder extends OrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false, precision = 8, scale = 2)
    @NotNull
    @Digits(integer=6,fraction=2)
    private float priceAtTimeOfPurchase;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Artwork artwork;

    public ArtworkOrder() {
    }

    public ArtworkOrder(float priceAtTimeOfPurchase, int quantity, OrderStatusEnum orderStatusEnum) {
        super(quantity, orderStatusEnum);
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArtworkOrder)) {
            return false;
        }
        ArtworkOrder other = (ArtworkOrder) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ArtworkOrder[ id=" + orderId + " ]";
    }

    /**
     * @return the priceAtTimeOfPurchase
     */
    public float getPriceAtTimeOfPurchase() {
        return priceAtTimeOfPurchase;
    }

    /**
     * @param priceAtTimeOfPurchase the priceAtTimeOfPurchase to set
     */
    public void setPriceAtTimeOfPurchase(float priceAtTimeOfPurchase) {
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }
    
    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }
}
