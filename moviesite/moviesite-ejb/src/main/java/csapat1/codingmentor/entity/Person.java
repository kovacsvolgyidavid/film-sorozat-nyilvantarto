package csapat1.codingmentor.entity;

import csapat1.codingmentor.enums.Sex;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    @Enumerated(EnumType.STRING)
    private Sex sex;
    
    @Column(name="YEAR_OF_BIRTH")
    private Integer yearOfBirth;
    
    @Column(name="PATH_OF_PHOTO")
    private String pathOfPhoto;

    public Person() {
        //it is bean
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPathOfPhoto() {
        return pathOfPhoto;
    }

    public void setPathOfPhoto(String pathOfPhoto) {
        this.pathOfPhoto = pathOfPhoto;
    }   
}
