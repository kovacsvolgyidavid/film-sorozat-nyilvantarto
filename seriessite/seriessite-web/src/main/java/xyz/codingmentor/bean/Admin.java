package xyz.codingmentor.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.enums.Groups;
import xyz.codingmentor.enums.Sex;
import xyz.codingmentor.service.EntityFacade;

@Named
@SessionScoped
public class Admin implements Serializable {

    @Inject
    private EntityFacade entityFacade;
    private final static Enum[] sexes = new Enum[2];
    private final static Enum[] ranks = new Enum[2];
    private User user;

    private List<User> users;
    private List<User> filteredUsers;

    @PostConstruct
    public void init() {

        sexes[0] = Sex.MALE;
        sexes[1] = Sex.FEMALE;

        ranks[0] = Groups.ADMIN;
        ranks[1] = Groups.USER;

        //createUsers(); //TODO: generálni adatbázist
        users = entityFacade.findAll(User.class);
    }

    public void deleteUser(User user) {
        User deletedUser = entityFacade.read(User.class, user.getUsername());
        users.remove(user);
        if (filteredUsers != null) {
            filteredUsers.remove(user);
        }
        entityFacade.delete(deletedUser);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

//    public List<String> getSexes() {
//        return Arrays.asList(sexes);
//    }
    public List<Enum> getSexes() {
        return Arrays.asList(sexes);
    }

    public List<Enum> getRanks() {
        return Arrays.asList(ranks);
    }

    public void resetFilteredUsers(AjaxBehaviorEvent e) {
        if (((UIOutput) e.getSource()).getValue() == null) {
            filteredUsers = null;
            System.out.println("ORRBA");
        }
    }

    public void rankValueChangeMethod(User user) {
        if (filteredUsers != null) {
            filteredUsers.remove(user);
            System.out.println("BA");
        }
        entityFacade.update(user);
        RequestContext.getCurrentInstance().update("form");
    }
 
    public void createUsers() {
        List<Groups> groups = new ArrayList<>();
        //groups.add(Groups.USER);

        user = new User();
        user.setName("Aron Kiss");
        user.setPassword(hashPassword("AronK0"));
        user.setUsername("kissaron");
//        user.setSex("Male");
        user.setSex(Sex.MALE);
        user.setPathOfPhoto("user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
//        user.setGroups(Groups.USER);
        user.setGroups(Groups.USER);
        entityFacade.create(user);

        user = new User();
        user.setName("Roland Feher");
        user.setPassword(hashPassword("RolandF2"));
        user.setUsername("froland");
//        user.setSex("Male");
        user.setSex(Sex.MALE);
        user.setPathOfPhoto("user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(Groups.USER);
        entityFacade.create(user);

        user = new User();

        user = new User();
        user.setName("Bela Nagy");
        user.setPassword(hashPassword("BaleN47"));
        user.setUsername("nagybela");
//        user.setSex("Male");
        user.setSex(Sex.MALE);
        user.setPathOfPhoto("user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(Groups.USER);
        entityFacade.create(user);

        user = new User();
        user.setName("Anita Kovacs");
        user.setPassword(hashPassword("AKovacs10"));
        user.setUsername("anita");
//        user.setSex("Female");
        user.setSex(Sex.FEMALE);
        user.setPathOfPhoto("user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(Groups.USER);
        entityFacade.create(user);

        user = new User();
        user.setName("Zsofia Horvath");
        user.setPassword(hashPassword("ZsH200"));
        user.setUsername("zsofi");
//        user.setSex("Female");
        user.setSex(Sex.FEMALE);
        user.setPathOfPhoto("user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(Groups.USER);
        entityFacade.create(user);
        
        user = new User();
        user.setName("Gabor Szabo");
        user.setPassword(hashPassword("Gabor500"));
        user.setUsername("gabor");
//        user.setSex("Female");
        user.setSex(Sex.MALE);
        user.setPathOfPhoto("user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(Groups.USER);
        entityFacade.create(user);
        
        user = new User();
        user.setName("Anett Kovacs");
        user.setPassword(hashPassword("Anett200"));
        user.setUsername("netti");
//        user.setSex("Female");
        user.setSex(Sex.FEMALE);
        user.setPathOfPhoto("user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(Groups.USER);
        entityFacade.create(user);
        
        user = new User();
        user.setName("Krisztian Farkas");
        user.setPassword(hashPassword("Krisztian100"));
        user.setUsername("krisztian");
//        user.setSex("Female");
        user.setSex(Sex.FEMALE);
        user.setPathOfPhoto("user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(Groups.USER);
        entityFacade.create(user);
        
        user = new User();
        user.setName("David Major");
        user.setPassword(hashPassword("David200"));
        user.setUsername("david");
//        user.setSex("Female");
        user.setSex(Sex.MALE);
        user.setPathOfPhoto("user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(Groups.USER);
        entityFacade.create(user);

        for (int i = 0; i < 200; i++) {
            user = new User();
            user.setName("Zsófia Horváth");
            user.setPassword(hashPassword("ZsH200"));
            user.setUsername("zsofi" + i);
//        user.setSex("Female");
            user.setSex(Sex.FEMALE);
            user.setPathOfPhoto("user.jpg");
            user.setDateOfBirth(new Date());
            user.setMoviePerPage(50);
            user.setGroups(Groups.USER);
            entityFacade.create(user);
        }

        user = new User();
    }

    public String hashPassword(String password) {
        String hashedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String text = password;
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashedPassword = bigInt.toString(16);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashedPassword;
    }
}
