package xyz.codingmentor.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    @JoinColumn(name = "SEASON_ID"/*, nullable = false*/)
    private Season season;
    
    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;
    
    @Column(name = "YEAR_OF_RELEASE")
    @Temporal(TemporalType.DATE)
    private Calendar yearOfRelease;

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

    public String getYearOfRelease() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(yearOfRelease.getTime());
    }

    public void setYearOfRelease(Calendar yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }  
}
