/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.validators;

import com.javier.utils.AppBundle;
import com.javier.utils.AppFacesContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author CAROLINA
 */
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, 
            Object value) throws ValidatorException {

        HtmlInputText htmlInputText = (HtmlInputText) component;
        String label;
        boolean isCreate = (boolean) component.getValueExpression("isCreate")
                                              .getValue(context.getELContext()) ;

        if (htmlInputText.getLabel() == null
                || htmlInputText.getLabel().trim().equals("")) {
            label = htmlInputText.getId();
        } else {
            label = htmlInputText.getLabel();
        }
        
        if ( (value == null || value.toString().isEmpty()) && isCreate == true ) {
                        FacesMessage facesMessage = new FacesMessage( label + ": " + 
                    AppBundle.getTextMessage("ERROR_MESSAGE_EMPTY_PASSWORD") 
            );

            throw new ValidatorException(facesMessage);
        }
        
        if ( (value.toString().length() < AppFacesContext.getMinPasswordSize()) && isCreate == true ) {
                        FacesMessage facesMessage = new FacesMessage( label + ": " + 
                    AppBundle.getTextMessage("ERROR_MESSAGE_PASSWORD_TOO_SHORT") 
            );

            throw new ValidatorException(facesMessage);
        }
                
        Pattern passwordPattern = Pattern.compile("^[a-zA-Z0-9\\s\\-\\.\\_]*$");
        Matcher matcher = passwordPattern.matcher((CharSequence) value);
        if (!matcher.matches() && isCreate == true ) {
            FacesMessage facesMessage = new FacesMessage( label + ": " + 
                    AppBundle.getTextMessage("ERROR_MESSAGE_NO_VALID_CHARACTERS_FOR_PASSWORD") 
            );

            throw new ValidatorException(facesMessage);
        }
    }
    
}
