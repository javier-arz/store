/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.validators;

import com.javier.utils.AppBundle;
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
@FacesValidator("emailValidator")
public class EmailValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, 
            Object value) throws ValidatorException {
        
        Pattern namePattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = namePattern.matcher((CharSequence) value);
        HtmlInputText htmlInputText = (HtmlInputText) component;
        String label;

        if (htmlInputText.getLabel() == null
                || htmlInputText.getLabel().trim().equals("")) {
            label = htmlInputText.getId();
        } else {
            label = htmlInputText.getLabel();
        }

        if (!matcher.matches()) {
            FacesMessage facesMessage = new FacesMessage( label + ": " + 
                    AppBundle.getTextMessage("ERROR_MESSAGE_NO_VALID_EMAIL_FORMAT")    
            );

            throw new ValidatorException(facesMessage);
        }
    }
    
    

}
