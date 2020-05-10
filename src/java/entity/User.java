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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import util.security.CryptographicHelper;

/**
 *
 * @author decimatum
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;
    
    
    @Column(unique=true,nullable = false, length = 30)
    @NotNull
    @Size(min = 4,max = 30)
    protected String username;
    
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    protected String password;
    
    @Column(nullable = false, length = 50)
    @NotNull
    @Size(min=2,max=50)
    protected String firstName;
    
    @Column(nullable = false, length = 50)
    @NotNull
    @Size(min=2,max=50)
    protected String lastName;
    
    @Column(nullable = false, length = 200)
    @NotNull
    @Size(min = 10, max = 200)
    @Pattern(regexp="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
    protected String email;
    
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    @Size(max = 32)
    protected String salt;
    
    @Column(columnDefinition = "boolean default false")
    @NotNull
    protected boolean isDeleted;


    
    @OneToMany(mappedBy = "user")
    protected List<Post> posts;
    @OneToMany(mappedBy = "user")
    protected List<Comment> comments;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Offences> offences;
    
    public User(){
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
    }

    public User(String username, String password, String firstName, String lastName, String email) {
        this();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        
        setPassword(password);
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password != null)
        {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        }
        else
        {
            this.password = null;
        }
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the offences
     */
    public List<Offences> getOffences() {
        return offences;
    }

    /**
     * @param offences the offences to set
     */
    public void setOffences(List<Offences> offences) {
        this.offences = offences;
    }
    
}
