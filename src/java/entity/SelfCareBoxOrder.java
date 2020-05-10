/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import util.enumeration.DurationEnum;
import util.enumeration.OrderStatusEnum;

/**
 *
 * @author Kaijing
 */
@Entity
public class SelfCareBoxOrder extends OrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false,precision = 5, scale = 2)
    @NotNull
    @Digits(integer=3,fraction=2)
    private float pricePerMthAtPurchase;
    
    @Column(nullable = false, precision = 6, scale = 2)
    @NotNull
    @Digits(integer=4,fraction=2)
    private float totalPriceAtPurchase;
    
    @Enumerated(EnumType.STRING)
    private DurationEnum durationEnum;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private SelfCareBox selfCareBox;

    public SelfCareBoxOrder() {
    }

    public SelfCareBoxOrder(float pricePerMthAtPurchase, DurationEnum durationEnum, int quantity, OrderStatusEnum orderStatusEnum) {
        super(quantity, orderStatusEnum);
        this.pricePerMthAtPurchase = pricePerMthAtPurchase;
        this.durationEnum = durationEnum;
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
        if (!(object instanceof SelfCareBoxOrder)) {
            return false;
        }
        SelfCareBoxOrder other = (SelfCareBoxOrder) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SelfCareBoxOrder[ id=" + orderId + " ]";
    }

    /**
     * @return the pricePerMthAtPurchase
     */
    public float getPricePerMthAtPurchase() {
        return pricePerMthAtPurchase;
    }

    /**
     * @param pricePerMthAtPurchase the pricePerMthAtPurchase to set
     */
    public void setPricePerMthAtPurchase(float pricePerMthAtPurchase) {
        this.pricePerMthAtPurchase = pricePerMthAtPurchase;
    }

    /**
     * @return the totalPriceAtPurchase
     */
    public float getTotalPriceAtPurchase() {
        return totalPriceAtPurchase;
    }

    /**
     * @param totalPriceAtPurchase the totalPriceAtPurchase to set
     */
    public void setTotalPriceAtPurchase(float totalPriceAtPurchase) {
        this.totalPriceAtPurchase = totalPriceAtPurchase;
    }

    /**
     * @return the durationEnum
     */
    public DurationEnum getDurationEnum() {
        return durationEnum;
    }

    /**
     * @param durationEnum the durationEnum to set
     */
    public void setDurationEnum(DurationEnum durationEnum) {
        this.durationEnum = durationEnum;
    }
    
    public SelfCareBox getSelfCareBox() {
        return selfCareBox;
    }

    public void setSelfCareBox(SelfCareBox selfCareBox) {
        this.selfCareBox = selfCareBox;
    }
    
}
