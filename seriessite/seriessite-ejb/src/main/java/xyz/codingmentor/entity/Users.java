package xyz.codingmentor.entity;

import xyz.codingmentor.enums.Groups;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import xyz.codingmentor.constraint.NameConstraint;
import xyz.codingmentor.constraint.UsernameConstraint;
import xyz.codingmentor.enums.Sex;

@Entity
@Table(name = "USERS")
@NamedQuery(name = "findUserByUsername",
        query = "SELECT u FROM Users u WHERE u.username = :username")
public class Users implements Serializable {

    @Id
    @Size(min = 1, message = "This field has to be filled.")
    @UsernameConstraint(message = "Wrong username format.")
    private String username;

    //@Size(min = 1, message = "This field has to be filled.")
    //@PasswordConstraint(message = "Wrong password format.")
    private String password;

    @Column(name = "MOVIE_PER_PAGE")
    private Integer moviePerPage;

    @CollectionTable(name = "groups")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Groups> groups = new ArrayList<>();
    
    @Size(min = 1, message = "This field has to be filled.")
    @NameConstraint(message = "Wrong name format.")
    private String name;

//    private String sex;
    private Sex sex;

    @Column(name = "DATE_OF_BIRTH")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "This field has to be filled.")
    private Date dateOfBirth;

    @Column(name = "PATH_OF_PHOTO")
    private String pathOfPhoto;

    @OneToMany(mappedBy = "users")
    private List<Comment> comments;

    public Users() {
        //Empty
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMoviePerPage() {
        return moviePerPage;
    }

    public void setMoviePerPage(Integer moviePerPage) {
        this.moviePerPage = moviePerPage;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    
    public Groups getRank() {
        return groups.get(0);
    }

    public void setRank(Groups rank) {
        groups.set(0, rank);
    }
}
