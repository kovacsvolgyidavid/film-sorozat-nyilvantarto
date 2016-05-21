package xyz.codingmentor.entity;

import xyz.codingmentor.enums.Groups;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import xyz.codingmentor.constraint.NameConstraint;
import xyz.codingmentor.constraint.UsernameConstraint;
import xyz.codingmentor.enums.Sex;

@Entity
@Table(name = "USERS")
@NamedQuery(name = "findUserByUsername", 
        query = "SELECT u FROM User u WHERE u.username = :username")
public class User implements Serializable {
    @Id
    @Size(min = 1, message = "This field has to be filled.")
    @UsernameConstraint(message = "Wrong username format.")
    private String username;
    
    @Column(name="password")
    private String hashedPassword;
    
    @Column(name="MOVIE_PER_PAGE")
    private Integer moviePerPage;
    
    @CollectionTable(name="groups")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Groups> groups=new HashSet<>();
    
     @Size(min = 1, message = "This field has to be filled.")
    @NameConstraint(message = "Wrong name format.")
    private String name;
    
    private Sex sex;
    
    @Column(name="DATE_OF_BIRTH")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "This field has to be filled.")
    private Date dateOfBirth;
    
    @Column(name="PATH_OF_PHOTO")
    private String pathOfPhoto;
    
@OneToMany (mappedBy = "user")
private List<Comment> comments;
    
    public User() {
        //Empty
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return hashedPassword;
    }

    public void setPassword(String password) {
        this.hashedPassword = password;
    }

    public Integer getMoviePerPage() {
        return moviePerPage;
    }

    public void setMoviePerPage(Integer moviePerPage) {
        this.moviePerPage = moviePerPage;
    }
    
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPathOfPhoto() {
        return pathOfPhoto;
    }

    public void setPathOfPhoto(String pathOfPhoto) {
        this.pathOfPhoto = pathOfPhoto;
    }
}
