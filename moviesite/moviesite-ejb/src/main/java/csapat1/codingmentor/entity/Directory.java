package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Directory extends Person implements Serializable {

    private String originalName;
    
    @OneToMany
    @JoinTable (name = "directory_and_film",
    joinColumns = @JoinColumn(name = "directory_fk"), 
    inverseJoinColumns = @JoinColumn(name = "film_fk") )
    private List<Film> films;

    public Directory() {
        //it is bean
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    

}
