package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Actor extends Person implements Serializable {

    private String placeOfBirth;

    private String officialWebsite;

    //ez így nem megy Film-re nem hivatkozhat mivel nem entitás
    /*@ManyToMany(mappedBy = "appearsOnFilms")
    private List<Film> films;*/
    
    public Actor() {
        //it is bean
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getOfficialWebsite() {
        return officialWebsite;
    }

    public void setOfficialWebsite(String officialWebsite) {
        this.officialWebsite = officialWebsite;
    }

    /*public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }*/ 
}
