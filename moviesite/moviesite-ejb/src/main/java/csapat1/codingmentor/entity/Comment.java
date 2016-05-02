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
public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;
    private String content;

    @Temporal(TemporalType.DATE)
    private Date dateOfComment;
    
    private Long idOfSeries;
    
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "film_fk", nullable = false)
    private Film film;
   

    public Comment() {
        //it is bean
    }

    
}
