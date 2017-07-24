/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.validators;

import com.javier.utils.AppBundle;
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
@FacesValidator("quantityValidator")
public class QuantityValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) 
            throws ValidatorException {
        int inputVal = (int)value ;        
        HtmlInputText htmlInputText = (HtmlInputText) component ;
        String label ;
        
        if ( htmlInputText == null || htmlInputText.getLabel().trim().isEmpty() ) {
            label = htmlInputText.getId();
        }
        else
        {
            label = htmlInputText.getLabel() ;
        }
        
        if ( inputVal < 0 ) {
            FacesMessage facesMessage = new FacesMessage( label + 
                    AppBundle.getTextMessage("ERROR_MESSAGE_INVALID_QTY") ) ;
            
            throw new ValidatorException(facesMessage) ;
        }
        
    }
    
}
