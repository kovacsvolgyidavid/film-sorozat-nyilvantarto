package csapat1.codingmentor.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String sex;

    private Integer yearOfBirth;
    
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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
