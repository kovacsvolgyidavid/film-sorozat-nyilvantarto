package csapat1.codingmentor.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Season implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    private String title;
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "SERIES_ID", nullable = false)
    private Series series;
    
    @OneToMany (mappedBy = "season")
    private Set<Episode> episodes;
    
    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;
    
    @Column(name = "YEAR_OF_RELEASE")
    @Temporal(TemporalType.DATE)
    private Calendar yearOfRelease;
    
    @Column(name = "LINK_OF_PROMO_VIDEO")
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

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getYearOfRelease() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(yearOfRelease.getTime());
    }

    public void setYearOfRelease(Calendar yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getLinkOfPromoVideo() {
        return linkOfPromoVideo;
    }

    public void setLinkOfPromoVideo(String linkOfPromoVideo) {
        this.linkOfPromoVideo = linkOfPromoVideo;
    }
}
