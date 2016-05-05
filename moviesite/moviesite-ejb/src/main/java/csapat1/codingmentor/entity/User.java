package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "USER_")
public class User extends Person implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    private String username;
    
    @NotNull
    @Pattern(regexp = "....")
    private String password;
    
    @OneToMany (mappedBy = "users")
    private List<Comment> comments;
    
    public User() {
        //it is bean
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }   
}
