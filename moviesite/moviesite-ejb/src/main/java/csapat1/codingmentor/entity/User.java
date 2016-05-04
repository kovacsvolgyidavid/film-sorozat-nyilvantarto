package csapat1.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    private String username;
    
    @NotNull
    @Pattern(regexp = "....")
    private String password;
    

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

}
