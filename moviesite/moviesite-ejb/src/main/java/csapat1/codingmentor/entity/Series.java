package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Series extends Movie implements Serializable {
    
    @OneToMany (mappedBy = "series")
    private Set<Season> seasons;
    
    public Series() {
        //it is bean
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
    }  
}
