/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entity.Customer;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kaijing
 */
@Entity
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;
    
    @Column(nullable = false, unique = true)
    @NotNull
    @Size(min=5,max=40)
    private String tagName;
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Customer> customers; 
    @ManyToMany(fetch = FetchType.LAZY)
    private List<SelfCareBox> selfCareBoxes;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Artwork> artworks;
    
    public Tag() {
        artworks = new ArrayList<>();
        customers = new ArrayList<>();
    }

    public Tag(String tagName) {
        this.tagName = tagName;
        //this.customers = new ArrayList<Customer>();
    }
    

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tagId != null ? tagId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the tagId fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.tagId == null && other.tagId != null) || (this.tagId != null && !this.tagId.equals(other.tagId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tag[ id=" + tagId + " ]";
    }

    /**
     * @return the tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * @param tagName the tagName to set
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<SelfCareBox> getSelfCareBoxes() {
        return selfCareBoxes;
    }

    public void setSelfCareBoxes(List<SelfCareBox> selfCareBoxes) {
        this.selfCareBoxes = selfCareBoxes;
    }
    
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }
}
