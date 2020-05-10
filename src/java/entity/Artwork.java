/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kaijing
 */
@Entity
public class Artwork implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artworkId;
    
    @Column(nullable = false, unique = true, length = 60)
    @NotNull
    @Size(min=1, max=60)
    private String name;
    
    @Column(nullable = false, length = 500)
    @NotNull
    @Size(min=5,max=500)
    private String description;
    
    @Column(nullable = false, length = 150)
    @NotNull
    @Size(min=5,max=150)
    private String image;
  
    @OneToMany(mappedBy = "artwork")
    private List<ArtworkOrder> artworkOrders;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Seller seller;
    @OneToMany(mappedBy = "artwork")
    private List<ArtworkPrice> artworkPrices;
    @ManyToMany(mappedBy = "artworks")
    private List<Tag> tags;
    

    public Artwork() {
        artworkPrices = new ArrayList<ArtworkPrice>();
        tags = new ArrayList<Tag>();
    }

    public Artwork(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Long getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Long artworkId) {
        this.artworkId = artworkId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artworkId != null ? artworkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the artworkId fields are not set
        if (!(object instanceof Artwork)) {
            return false;
        }
        Artwork other = (Artwork) object;
        if ((this.artworkId == null && other.artworkId != null) || (this.artworkId != null && !this.artworkId.equals(other.artworkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Artwork[ id=" + artworkId + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
 
    public List<ArtworkOrder> getArtworkOrders() {
        return artworkOrders;
    }

    public void setArtworkOrders(List<ArtworkOrder> artworkOrders) {
        this.artworkOrders = artworkOrders;
    }
    
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<ArtworkPrice> getArtworkPrices() {
        return artworkPrices;
    }

    public void setArtworkPrices(List<ArtworkPrice> artworkPrices) {
        this.artworkPrices = artworkPrices;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    
    
    public void addTag(Tag tag){
        if(tag != null){
            if(!this.tags.contains(tag)){
                this.tags.add(tag);
                if(tag.getArtworks().contains(this)){
                    tag.getArtworks().add(this);
                }
            }
        }
    }
    
    public void removeTag(Tag tag){
        if(tag != null){
            if(!this.tags.contains(tag)){
                this.tags.remove(tag);
                if(tag.getArtworks().contains(this)){
                    tag.getArtworks().remove(this);
                }
            }
        }
    }
}
