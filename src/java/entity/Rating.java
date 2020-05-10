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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Kaijing
 */
@Entity
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;
    
    @Column(nullable = false, precision = 3,scale=2)
    @NotNull
    @Min(1)
    @Max(5)
    private float numOfStars;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private SelfCareBox selfCareBox;
    
    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratingId != null ? ratingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the ratingId fields are not set
        if (!(object instanceof Rating)) {
            return false;
        }
        Rating other = (Rating) object;
        if ((this.ratingId == null && other.ratingId != null) || (this.ratingId != null && !this.ratingId.equals(other.ratingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rating[ id=" + ratingId + " ]";
    }

    /**
     * @return the numOfStars
     */
    public float getNumOfStars() {
        return numOfStars;
    }

    /**
     * @param numOfStars the numOfStars to set
     */
    public void setNumOfStars(float numOfStars) {
        this.numOfStars = numOfStars;
    }
    
    public SelfCareBox getSelfCareBox() {
        return selfCareBox;
    }

    public void setSelfCareBox(SelfCareBox selfCareBox) {
        this.selfCareBox = selfCareBox;
    }
    
}
