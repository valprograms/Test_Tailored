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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import util.enumeration.DurationEnum;

/**
 *
 * @author Kaijing
 */
@Entity
public class SelfCareSubscriptionDiscount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;
    
    @Column(columnDefinition = "integer default 0 NOT NULL")
    @NotNull
    @Min(0)
    @Max(100)
    private float discountPercentage;
    
    @Enumerated(EnumType.STRING)
    private DurationEnum durationEnum;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private SelfCareBox selfCareBox;

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public SelfCareSubscriptionDiscount() {
    }
    

    public SelfCareSubscriptionDiscount(int discountPercentage, DurationEnum durationEnum) {
        this.discountPercentage = discountPercentage;
        this.durationEnum = durationEnum;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (discountId != null ? discountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the discountId fields are not set
        if (!(object instanceof SelfCareSubscriptionDiscount)) {
            return false;
        }
        SelfCareSubscriptionDiscount other = (SelfCareSubscriptionDiscount) object;
        if ((this.discountId == null && other.discountId != null) || (this.discountId != null && !this.discountId.equals(other.discountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SelfCareSubscriptionDiscount[ id=" + discountId + " ]";
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
    
    public String getDurationEnumStr() {
        if(this.durationEnum.equals(DurationEnum.OnceOff)){
            return "OnceOff";
        } else if(this.durationEnum.equals(DurationEnum.ThreeMonths)){
            return "ThreeMonths";
        } else {
            return "SixMonths";
        }
    }
    
    public void setFormatEnumString(String FormatEnumStr){
        if(FormatEnumStr.equals("OnceOff")){
            this.durationEnum = DurationEnum.OnceOff;
        } else if(FormatEnumStr.equals("ThreeMonths")){
            this.durationEnum = DurationEnum.ThreeMonths;
        }else {
            this.durationEnum = DurationEnum.SixMonths;
        } 
    }
    public SelfCareBox getSelfCareBox() {
        return selfCareBox;
    }

    public void setSelfCareBox(SelfCareBox selfCareBox) {
        this.selfCareBox = selfCareBox;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    
}
