package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import xyz.codingmentor.constraint.UsernameConstraint;

@Entity
@Table(name = "USERS")
@NamedQuery(name = "findUserByUsername", 
        query = "SELECT u FROM Users u WHERE u.username = :username")
public class Users extends Person implements Serializable {
    @Id
    @Size(min = 1, message = "This field has to be filled.")
    @UsernameConstraint(message = "Wrong username format.")
    private String username;
    
    //@Size(min = 1, message = "This field has to be filled.")
    //@PasswordConstraint(message = "Wrong password format.")
    private String password;
    
    @Column(name="MOVIE_PER_PAGE")
    private Integer moviePerPage;
    
    @CollectionTable(name="groups")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Groups> groups=new HashSet<>();
//    private String rank;
    
//    @OneToMany (mappedBy = "users")
//    private List<Comment> comments;
    
    public Users() {
        //Empty
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
        this.password = password;
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
    
}
