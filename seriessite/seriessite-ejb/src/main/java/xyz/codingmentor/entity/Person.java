package xyz.codingmentor.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import xyz.codingmentor.enums.Sex;

@MappedSuperclass
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Size(min = 1, message = "This field has to be filled.")
    @Pattern(regexp = "^([\\p{Lu}]{1}[\\p{Ll}]{1,30}[- ]{0,1}|"
            + "[\\p{Lu}]{1}[- \\']{1}[\\p{Lu}]{0,1}[\\p{Ll}]{1,30}[- ]{0,1}|[\\p{Ll}]{1,2}"
            + "[ -\\']{1}[\\p{Lu}]{1}[\\p{Ll}]{1,30}){2,5}$", message = "Wrong name format.")
    private String name;

    private Sex sex;

    @Column(name = "DATE_OF_BIRTH")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "This field has to be filled.")
    private Date dateOfBirth;

    @Column(name = "PATH_OF_PHOTO")
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPathOfPhoto() {
        return pathOfPhoto;
    }

    public void setPathOfPhoto(String pathOfPhoto) {
        this.pathOfPhoto = pathOfPhoto;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", name=" + name + ", sex=" + sex + ", dateOfBirth=" + dateOfBirth + ", pathOfPhoto=" + pathOfPhoto + '}';
    }

}
