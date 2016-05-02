package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public class Film implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "directory_fk", nullable = false)
    private Directory directory;
    
    
    @ManyToMany
    @JoinTable (name = "movie_and_actor",
    joinColumns = @JoinColumn(name = "movie_fk"), 
    inverseJoinColumns = @JoinColumn(name = "actor_fk ") )
    public List<Actor> actors;

    public Film() {
        //it is bean
    }

    

}
