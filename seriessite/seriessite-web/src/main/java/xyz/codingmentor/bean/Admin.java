package xyz.codingmentor.bean;

import java.io.Serializable;
import java.util.List;
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
    private Enum[] sexes;
    private Enum[] ranks;
    private User user;

    private List<User> users;
    private List<User> filteredUsers;

    @PostConstruct
    public void init() {
        sexes = Sex.class.getEnumConstants();
        ranks = Groups.class.getEnumConstants();
        
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

    public Enum[] getSexes() {
        return sexes;
    }

    public Enum[] getRanks() {
        return ranks;
    }

    public void resetFilteredUsers(AjaxBehaviorEvent e) {
        if (((UIOutput) e.getSource()).getValue() == null) {
            filteredUsers = null;
        }
    }

    public void rankValueChangeMethod(User user) {
        if (filteredUsers != null) {
            filteredUsers.remove(user);
        }
        entityFacade.update(user);
        RequestContext.getCurrentInstance().update("form");
    }
}
