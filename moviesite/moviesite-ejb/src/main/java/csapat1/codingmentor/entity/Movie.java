package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Movie extends Film implements Serializable {
    
    @NotNull
    private String title;
    
    @Temporal(TemporalType.DATE)
    private Date yearOfRelease;

    public Movie() {
        //it is bean
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Date yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }
}
