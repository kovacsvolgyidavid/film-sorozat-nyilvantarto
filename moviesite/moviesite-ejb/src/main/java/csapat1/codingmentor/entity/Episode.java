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
import javax.validation.constraints.NotNull;

@Entity
public class Episode implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
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
