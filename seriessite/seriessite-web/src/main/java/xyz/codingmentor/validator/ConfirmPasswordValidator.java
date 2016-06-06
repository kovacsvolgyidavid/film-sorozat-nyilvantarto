package xyz.codingmentor.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("confirmPasswordValidator")
public class ConfirmPasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String password = (String) component.getAttributes().get("confirm");
        String confirm = (String) value;

        if (password == null || confirm == null) {
            return;
        }

        if (!password.equals(confirm)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Passwords have to be the same."));          
        }
    }
}
