package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import xyz.codingmentor.enums.CompareValues;

@Entity
public class Series extends Movie implements Serializable {

    @OneToMany(mappedBy = "series")
    private List<Season> seasons;

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public Series() {
        //it is bean
    }
     
}
