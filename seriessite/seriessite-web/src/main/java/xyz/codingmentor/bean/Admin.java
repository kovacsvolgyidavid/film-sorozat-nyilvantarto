package xyz.codingmentor.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import xyz.codingmentor.entity.Users;
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
    private Users user;
    
    
    private List<Users> users;
    private List<Users> filteredUsers;

    @PostConstruct
    public void init() {
//        sexes[0] = "Male";
//        sexes[1] = "Female";
        
        sexes[0] = Sex.MALE;
        sexes[1] = Sex.FEMALE;
        
        ranks[0] = Groups.ADMIN;
        ranks[1] = Groups.USER;

        createUsers();
        users = entityFacade.findAll(Users.class);
    }
    
    public void deleteUser(Users user){
        Users deletedUser = entityFacade.read(Users.class, user.getUsername());
        users.remove(user);
        entityFacade.delete(deletedUser);
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Users> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<Users> filteredUsers) {
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
    
    public void rankValueChangeMethod(AjaxBehaviorEvent e) {
        UIData data = (UIData) e.getComponent().findComponent("table");
        int rowIndex = data.getRowIndex();
        entityFacade.update(users.get(rowIndex)); 
    }
    
    public void createUsers(){
        List<Groups> groups = new ArrayList<>();
        groups.add(Groups.USER);
        
        user = new Users();
        user.setName("Aron Kiss");
        user.setPassword("AronK0");
        user.setUsername("kissaron");
//        user.setSex("Male");
        user.setSex(Sex.MALE);
        user.setPathOfPhoto("\\path\\resources\\user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(groups);
        entityFacade.create(user);
        
        user = new Users();
        user.setName("Roland Feher");
        user.setPassword("RolandF2");
        user.setUsername("froland");
//        user.setSex("Male");
        user.setSex(Sex.MALE);
        user.setPathOfPhoto("\\path\\resources\\user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(groups);
        entityFacade.create(user);
        
        user = new Users();
        
        user = new Users();
        user.setName("Bela Nagy");
        user.setPassword("BaleN47");
        user.setUsername("nagybela");
//        user.setSex("Male");
        user.setSex(Sex.MALE);
        user.setPathOfPhoto("\\path\\resources\\user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(groups);
        entityFacade.create(user);
        
        user = new Users();
        user.setName("Anita Kovacs");
        user.setPassword("AKovacs10");
        user.setUsername("anita");
//        user.setSex("Female");
        user.setSex(Sex.FEMALE);
        user.setPathOfPhoto("\\path\\resources\\user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(groups);
        entityFacade.create(user);
        
        user = new Users();
        user.setName("Zsófia Horváth");
        user.setPassword("ZsH200");
        user.setUsername("zsofi");
//        user.setSex("Female");
        user.setSex(Sex.FEMALE);
        user.setPathOfPhoto("\\path\\resources\\user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setGroups(groups);
        entityFacade.create(user);
        
        user = new Users();
    }
    
    public String hashPassword(String password){
        String hashedPassword = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String text = user.getPassword();
            md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashedPassword = bigInt.toString(16);

            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashedPassword;     
    }
}
