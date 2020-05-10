/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.FormatEnum;

/**
 *
 * @author Kaijing
 */
@Entity
public class ArtworkPrice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artworkPriceId;
    
    @Column(nullable = false, precision = 8, scale = 2)
    @NotNull
    @Digits(integer=6,fraction=2)
    private float price;
    
    @Enumerated(EnumType.STRING)
    private FormatEnum formatEnum;
    
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Artwork artwork;

    public ArtworkPrice() {
    }

    public ArtworkPrice(float price, FormatEnum formatEnum) {
        this.price = price;
        this.formatEnum = formatEnum;
    }

    public Long getArtworkPriceId() {
        return artworkPriceId;
    }

    public void setArtworkPriceId(Long artworkPriceId) {
        this.artworkPriceId = artworkPriceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artworkPriceId != null ? artworkPriceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the artworkPriceId fields are not set
        if (!(object instanceof ArtworkPrice)) {
            return false;
        }
        ArtworkPrice other = (ArtworkPrice) object;
        if ((this.artworkPriceId == null && other.artworkPriceId != null) || (this.artworkPriceId != null && !this.artworkPriceId.equals(other.artworkPriceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ArtworkPrice[ id=" + artworkPriceId + " ]";
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the formatEnum
     */
    public String getFormatEnumStr() {
        if(this.formatEnum.equals(FormatEnum.ARTPOSTER)){
            return "Art Poster";
        } else if(this.formatEnum.equals(FormatEnum.CANVASWRAP)){
            return "Canvas Wrap";
        } else {
            return "Photo Print";
        }
    }
    
    public void setFormatEnumString(String FormatEnumStr){
        if(FormatEnumStr.equals("Art Poster")){
            this.formatEnum = FormatEnum.ARTPOSTER;
        } else if(FormatEnumStr.equals("Canvas Wrap")){
            this.formatEnum = FormatEnum.CANVASWRAP;
        }else {
            this.formatEnum = FormatEnum.PHOTOPRINT;
        } 
    }

    public FormatEnum getFormatEnum() {
        return formatEnum;
    }
    
    
    /**
     * @param formatEnum the formatEnum to set
     */
    public void setFormatEnum(FormatEnum formatEnum) {
        this.formatEnum = formatEnum;
    }
    
    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }
}
