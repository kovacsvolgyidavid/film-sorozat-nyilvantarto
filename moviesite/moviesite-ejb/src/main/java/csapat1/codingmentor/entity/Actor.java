package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Actor extends Person implements Serializable {

    private String placeOfBirth;

    private String officialWebsite;

    @ManyToMany(mappedBy = "appearsOnFilms")
    private List<Film> films;
    
    public Actor() {
        //it is bean
    }

      
    

}
