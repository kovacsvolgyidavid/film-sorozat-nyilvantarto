package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import xyz.codingmentor.constraint.PasswordConstraint;
import xyz.codingmentor.constraint.UsernameConstraint;

@Entity
@Table(name = "USER_")
@NamedQuery(name = "findUserByUsername", 
        query = "SELECT u FROM User u WHERE u.username = :username")
public class User extends Person implements Serializable {
    //private static final Long serialVersionUID = -4723388127417782962L;
    
    @Size(min = 1, message = "This field has to be filled.")
    @UsernameConstraint(message = "Wrong username format.")
    private String username;
    
    @Size(min = 1, message = "This field has to be filled.")
    @PasswordConstraint(message = "Wrong password format.")
    private String password;
    
    @Column(name="MOVIE_PER_PAGE")
    private Integer moviePerPage;
    
    private String rank;
    
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }  
}
