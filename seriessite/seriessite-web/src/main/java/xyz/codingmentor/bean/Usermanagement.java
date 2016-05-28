package xyz.codingmentor.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import xyz.codingmentor.service.VisitorCounter;

@Named
@SessionScoped
public class Usermanagement implements Serializable {
    
    @Inject
    VisitorCounter visitorCounter;
    
    @PostConstruct
    public void increaseVisitors(){
        visitorCounter.setVisitor(visitorCounter.getVisitor()+1);
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
    
    public String getActualUser(){
        return FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }

    public boolean isUserLoggedIn() {
        String user = this.getUsername();
        boolean result = !((user == null) || user.isEmpty());
        return result;
    }

    public static String getUsername() {
        String user = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return user;
    }    
}
