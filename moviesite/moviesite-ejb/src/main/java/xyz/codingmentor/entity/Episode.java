package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "SEASON_ID")
    private Season season;
    
    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;
    
    @Column(name = "DATE_OF_RELEASE")
    @Temporal(TemporalType.DATE)
    private Date dateOfRelease;

    public Episode() {
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

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
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
}
