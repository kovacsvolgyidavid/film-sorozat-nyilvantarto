package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Episode implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "season_fk", nullable = false)
    private Season season;
    
    private String serialNumber;
    
    @Temporal(TemporalType.DATE)
    private Date yearOfRelease;

    public Episode() {
        //it is bean
    }

}
