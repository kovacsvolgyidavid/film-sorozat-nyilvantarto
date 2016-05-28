package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
    @NamedQuery(name = "Season.findSeasonById",
            query = "SELECT s FROM Season s WHERE s.id = :id"),
    @NamedQuery(name = "Season.findAll",
            query = "SELECT s FROM Season s")
})
@Entity
public class Season implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERIES_ID"/*, nullable = false*/)
    private Series series;

    @OneToMany(mappedBy = "season")
    private List<Episode> episodes;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "DATE_OF_RELEASE")
    @Temporal(TemporalType.DATE)
    private Date dateOfRelease;

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

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(Date dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public String getLinkOfPromoVideo() {
        return linkOfPromoVideo;
    }

    public void setLinkOfPromoVideo(String linkOfPromoVideo) {
        this.linkOfPromoVideo = linkOfPromoVideo;
    }
}
