/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kaijing
 */
@Entity
public class SelfCareBox implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long selfCareBoxId;
    
    @Column(nullable = false, length = 60)
    @NotNull
    @Size(min=10,max=60)
    private String name;
    
    @Column(nullable = false, length = 500)
    @NotNull
    @Size(min=10,max=500)
    private String description;
    
    @Column(nullable = false, precision = 5, scale = 2)
    @NotNull
    @Digits(integer=3,fraction=2)
    private float pricePerMonth;
    
    @Column(nullable = false, length = 150)
    @NotNull
    @Size(min = 10, max = 150)
    private String image;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Seller seller;
    @OneToMany(mappedBy = "selfCareBox")
    private List<SelfCareSubscriptionDiscount> selfCareSubscriptionDiscounts;
    @OneToMany(mappedBy = "selfCareBox")
    private List<SelfCareBoxOrder> selfCareBoxOrders;
    @OneToMany(mappedBy = "selfCareBox")
    private List<Review> reviews;
    @OneToMany(mappedBy = "selfCareBox")
    private List<Rating> ratings;
    @ManyToMany(mappedBy = "selfCareBoxes")
    private List<Tag> tags;

    public SelfCareBox() {
        selfCareSubscriptionDiscounts = new ArrayList<SelfCareSubscriptionDiscount>();
        reviews = new ArrayList<Review>();
        ratings = new ArrayList<Rating>();
        tags = new ArrayList<Tag>();
    }

    public SelfCareBox(String name, String description, float pricePerMonth, String image) {
        this.name = name;
        this.description = description;
        this.pricePerMonth = pricePerMonth;
        this.image = image;
        reviews = new ArrayList<Review>();
        ratings = new ArrayList<Rating>();
        tags = new ArrayList<Tag>();
    }
    
    

    public Long getSelfCareBoxId() {
        return selfCareBoxId;
    }

    public void setSelfCareBoxId(Long selfCareBoxId) {
        this.selfCareBoxId = selfCareBoxId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (selfCareBoxId != null ? selfCareBoxId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the selfCareBoxId fields are not set
        if (!(object instanceof SelfCareBox)) {
            return false;
        }
        SelfCareBox other = (SelfCareBox) object;
        if ((this.selfCareBoxId == null && other.selfCareBoxId != null) || (this.selfCareBoxId != null && !this.selfCareBoxId.equals(other.selfCareBoxId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SelfCareBox[ id=" + selfCareBoxId + " ]";
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

    /**
     * @return the pricePerMonth
     */
    public float getPricePerMonth() {
        return pricePerMonth;
    }

    /**
     * @param pricePerMonth the pricePerMonth to set
     */
    public void setPricePerMonth(float pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }
    
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<SelfCareSubscriptionDiscount> getSelfCareSubscriptionDiscounts() {
        return selfCareSubscriptionDiscounts;
    }

    public void setSelfCareSubscriptionDiscounts(List<SelfCareSubscriptionDiscount> selfCareSubscriptionDiscounts) {
        this.selfCareSubscriptionDiscounts = selfCareSubscriptionDiscounts;
    }

    public List<SelfCareBoxOrder> getSelfCareBoxOrders() {
        return selfCareBoxOrders;
    }

    public void setSelfCareBoxOrders(List<SelfCareBoxOrder> selfCareBoxOrders) {
        this.selfCareBoxOrders = selfCareBoxOrders;
    }
       
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
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
                if(tag.getSelfCareBoxes().contains(this)){
                    tag.getSelfCareBoxes().add(this);
                }
            }
        }
    }
    
    public void removeTag(Tag tag){
        if(tag != null){
            if(!this.tags.contains(tag)){
                this.tags.remove(tag);
                if(tag.getSelfCareBoxes().contains(this)){
                    tag.getSelfCareBoxes().remove(this);
                }
            }
        }
    }
    
    public void addSelfCareSubscriptionDiscount(SelfCareSubscriptionDiscount discount){
        if(discount!= null){
            if(!this.selfCareSubscriptionDiscounts.contains(discount)){
                this.selfCareSubscriptionDiscounts.add(discount);
                if(discount.getSelfCareBox().equals(this)){
                    discount.setSelfCareBox(this);
                }
            }
        }
    }
    
    public void removeSelfCareSubscriptionDiscount(SelfCareSubscriptionDiscount discount){
        if(discount!= null){
            if(!this.selfCareSubscriptionDiscounts.contains(discount)){
                this.selfCareSubscriptionDiscounts.remove(discount);
                if(discount.getSelfCareBox().equals(this)){
                    discount.setSelfCareBox(null);
                }
            }
        }
    }
}
