package xyz.codingmentor.bean;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.service.EntityFacade;

@Named
@SessionScoped
public class Login implements Serializable {

    @Inject
    private EntityFacade entityFacade;
    
    @Inject
    private Registration registration;
    
    private User user;

    @PostConstruct
    public void init() {
        registration.createDirectory();
        
        user = new User();
        user.setName("Aron Kiss");
        user.setPassword("AronK0");
        user.setUsername("kissaron");
        user.setSex("Male");
        user.setPathOfPhoto("\\path\\resources\\user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setRank("User");
        entityFacade.create(user);
        
        user = new User();
        user.setName("Roland Feher");
        user.setPassword("RolandF2");
        user.setUsername("froland");
        user.setSex("Male");
        user.setPathOfPhoto("\\path\\resources\\user.jpg");
        user.setDateOfBirth(new Date());
        user.setMoviePerPage(50);
        user.setRank("Admin");
        entityFacade.create(user);
        
        user = new User();
    }

    public String login() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        User userFromDatabase;
        TypedQuery<User> username = entityFacade.getEntityManager().createNamedQuery("findUserByUsername", User.class);
        username.setParameter("username", user.getUsername());

        try {
            userFromDatabase = username.getSingleResult();
        } catch (NoResultException noResultException) {
            user.setUsername("");
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong username or password!", "Error!"));
            return "";
        }

        if (user.getPassword().equals(userFromDatabase.getPassword())) {
            switch (userFromDatabase.getRank()) {
                case "User":
                    return "user?faces-redirect=true";
                case "Admin":
                    return "admin?faces-redirect=true";
                default: {
                    return "";
                }
            }
        } else {
            user.setUsername("");
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong username or password!", "Error!"));
            return "";
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
