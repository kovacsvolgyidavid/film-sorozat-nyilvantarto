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
public class Season implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "series_fk", nullable = false)
    private Series series;
    
    private String serialNumber;
    
    @Temporal(TemporalType.DATE)
    private Date yearOfRelease;

    private String linkOfPromoVideo;

    public Season() {
        //it is bean
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Date yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getLinkOfPromoVideo() {
        return linkOfPromoVideo;
    }

    public void setLinkOfPromoVideo(String linkOfPromoVideo) {
        this.linkOfPromoVideo = linkOfPromoVideo;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
