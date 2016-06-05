package xyz.codingmentor.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import xyz.codingmentor.entity.User;
import xyz.codingmentor.service.EntityFacade;

@FacesValidator("existingUsernameValidator")
public class ExistingUsernameValidator implements Validator{
    @Inject
    private EntityFacade entityFacade;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String username = (String) value;
        
        if (entityFacade.read(User.class, username) != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "This uername is already taken.")); 
        }   
    }
}

