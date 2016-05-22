package xyz.codingmentor.bean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author basstik
 */
@Named
@RequestScoped
public class Usermanagement implements Serializable {

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "/login.xhtml";
    }
    
    public String getActualUser(){
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public boolean isUserLoggedIn() {
        String user = this.getUsername();
        boolean result = !((user == null) || user.isEmpty());
        return result;
    }

    public String getUsername() {
        String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return user;
    }
}
