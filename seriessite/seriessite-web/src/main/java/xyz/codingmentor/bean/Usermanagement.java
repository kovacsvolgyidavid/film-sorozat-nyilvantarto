package xyz.codingmentor.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.service.VisitorCounter;

/**
 *
 * @author Dávid Kovácsvölgyi <kovacsvolgyi.david@gmail.com>
 */
@Named
@SessionScoped
public class Usermanagement implements Serializable {

    @Inject
    VisitorCounter visitorCounter;

    @PostConstruct
    public void increaseVisitorNumberByOne() {
        visitorCounter.increaseVisitorNumberByOne();
    }

    public VisitorCounter getVisitorCounter() {
        return visitorCounter;
    }

    public void setVisitorCounter(VisitorCounter visitorCounter) {
        this.visitorCounter = visitorCounter;
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "/login.xhtml?faces-redirect=true";
    }

    public String getActualUser() {
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public static String getUsername() {
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

}
