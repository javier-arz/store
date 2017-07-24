/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.validators;

import com.javier.utils.AppBundle;
import java.util.ResourceBundle;
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
@FacesValidator("nameValidator")
public class NameValidator implements Validator {
    
    //private ResourceBundle bundle = ResourceBundle.getBundle("com.javier.texts.messages");

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {

        Pattern namePattern = Pattern.compile("^[a-zA-Z0-9\\s\\-\\.\\_]*$");
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
            FacesMessage facesMessage = new FacesMessage(
//                    label + ": " + bundle.getString("NO_VALID_CHARACTERS_FOR_NAME")
                    label + ": " + AppBundle.getTextMessage("NO_VALID_CHARACTERS_FOR_NAME") 
            );

            throw new ValidatorException(facesMessage);
        }
    }

}
