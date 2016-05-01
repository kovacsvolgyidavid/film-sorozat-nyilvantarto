package entity;

import enums.Sex;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author David Kovacsvolgyi <kovacsvolgyi.david@gmail.com>
 */
@MappedSuperclass
public class Person {
    @Id@GeneratedValue
    private Long id;
    private String name;
    private Date birthDay;
    private String imageURL;
    private Sex sex;

    public Person() {
        //FOR MAPPING REASONS
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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    
}
