package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Series extends Film implements Serializable {
    
    private String title;

    private String pathOfPhoto;
    
    @OneToMany
    @JoinTable (name = "series_and_season",
    joinColumns = @JoinColumn(name = "series_fk"), 
    inverseJoinColumns = @JoinColumn(name = "season_fk ") )
    private List<Season> seasons;



    public Series() {
        //it is bean
    }

}
