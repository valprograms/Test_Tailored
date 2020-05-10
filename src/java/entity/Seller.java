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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Kaijing
 */
@Entity
public class Seller extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = true, columnDefinition = "boolean default false")
    private boolean isBanned;
    
    @Column(nullable = true,columnDefinition = "boolean default false")
    private boolean isVerified;
    
    @OneToMany(mappedBy = "seller")
    private List<Artwork> artworks;
    @OneToMany(mappedBy = "seller")
    private List<SelfCareBox> selfCareBoxes;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Customer> customers;

    public Seller() {
        super();
        artworks = new ArrayList<Artwork>();
        selfCareBoxes = new ArrayList<SelfCareBox>();
        customers = new ArrayList<Customer>();
    }

    public Seller(String username, String password, String firstName, String lastName, String email) {
        super(username, password, firstName, lastName, email);
        this.isBanned = false;
        this.isVerified = false;
        artworks = new ArrayList<Artwork>();
        selfCareBoxes = new ArrayList<SelfCareBox>();
        customers = new ArrayList<Customer>();
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seller)) {
            return false;
        }
        Seller other = (Seller) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Seller[ id=" + userId + " ]";
    }

    /**
     * @return the isBanned
     */
    public boolean isIsBanned() {
        return isBanned;
    }

    /**
     * @param isBanned the isBanned to set
     */
    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }

    /**
     * @return the isVerified
     */
    public boolean isIsVerified() {
        return isVerified;
    }

    /**
     * @param isVerified the isVerified to set
     */
    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
    
    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
    
    public List<SelfCareBox> getSelfCareBoxes() {
        return selfCareBoxes;
    }

    public void setSelfCareBoxes(List<SelfCareBox> selfCareBoxes) {
        this.selfCareBoxes = selfCareBoxes;
    }

    /**
     * @return the customers
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * @param customers the customers to set
     */
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
    public void addArtwork(Artwork artwork){
        if(artworks != null){
            if(!this.artworks.contains(artwork)){
                this.artworks.add(artwork);
                if(!artwork.getSeller().equals(null)){
                    artwork.setSeller(this);
                }
            }
        } 
    }
    
    public void removeArtwork(Artwork artwork){
        if(artworks != null){
            if(!this.artworks.contains(artwork)){
                this.artworks.remove(artwork);
                if(artwork.getSeller().equals(this)){
                    artwork.setSeller(null);
                }
            }
        } 
    }
    public void addSelfCareBox(SelfCareBox selfCareBox){
        if(selfCareBoxes != null){
            if(!this.selfCareBoxes.contains(selfCareBox)){
                this.selfCareBoxes.add(selfCareBox);
                if(!selfCareBox.getSeller().equals(null)){
                    selfCareBox.setSeller(this);
                }
            }
        } 
    }
    
    public void removeSelfCareBox(SelfCareBox selfCareBox){
        if(selfCareBoxes != null){
            if(!this.selfCareBoxes.contains(selfCareBox)){
                this.selfCareBoxes.remove(selfCareBox);
                if(selfCareBox.getSeller().equals(this)){
                    selfCareBox.setSeller(null);
                }
            }
        } 
    }   
}
