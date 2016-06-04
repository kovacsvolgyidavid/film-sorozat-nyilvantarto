package xyz.codingmentor.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import xyz.codingmentor.bean.Profile;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.service.EntityFacade;

@FacesValidator("currentPasswordValidator")
public class CurrentPasswordValidator implements Validator{
    @Inject
    private EntityFacade entityFacade;
    
    @Inject
    private Profile profile;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String passwordFromTable;
        String oldPassword = (String) value;
        
        if (profile.isEditMyProfile()) {
            passwordFromTable = entityFacade.read(User.class, profile.getUser().getUsername()).getPassword();
        } else {
            passwordFromTable = entityFacade.read(User.class, facesContext.getExternalContext().getRemoteUser()).getPassword();
        }

        if (!profile.hashPassword(oldPassword).equals(passwordFromTable)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Your password is incorrect."));  
        }                
    }  
}
