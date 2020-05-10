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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Kaijing
 */
@Entity
public class Offences implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offencesId;

    @Column(unique=true,nullable = false, length = 40)
    @NotNull
    private String name;
    
    @Column(nullable = true, length = 500)
    private String description;
    
    @Column(nullable = false, precision = 2)
    @NotNull
    @Min(1)
    @Max(10)
    private int numOfPoints;
    
    @ManyToMany(mappedBy = "offences", fetch = FetchType.LAZY)
    private List<User> users;

    public Offences() {
    }

    public Offences(String name, String description, int numOfPoints) {
        this.name = name;
        this.description = description;
        this.numOfPoints = numOfPoints;
    }

    public Long getOffencesId() {
        return offencesId;
    }

    public void setOffencesId(Long offencesId) {
        this.offencesId = offencesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (offencesId != null ? offencesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the offencesId fields are not set
        if (!(object instanceof Offences)) {
            return false;
        }
        Offences other = (Offences) object;
        if ((this.offencesId == null && other.offencesId != null) || (this.offencesId != null && !this.offencesId.equals(other.offencesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Offences[ id=" + offencesId + " ]";
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
     * @return the numOfPoints
     */
    public int getNumOfPoints() {
        return numOfPoints;
    }

    /**
     * @param numOfPoints the numOfPoints to set
     */
    public void setNumOfPoints(int numOfPoints) {
        this.numOfPoints = numOfPoints;
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
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
}
