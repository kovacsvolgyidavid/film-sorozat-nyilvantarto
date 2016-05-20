package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Series.findSerieByName", 
        query = "SELECT s FROM Series s WHERE s.title = :title")
public class Series extends Movie implements Serializable {
    
    @OneToMany (mappedBy = "series")
    private List<Season> seasons;
    
    private String description;
    
    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }       
    
    public Series() {
        //it is bean
    } 

    @Override
    public String toString() {
        return "Series{" + "seasons=" + seasons + '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTitle() {
        return super.getTitle(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getId() {
        return super.getId(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
