/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author CAROLINA
 */
public class AppMessages {
    
    public static void addMesage(Severity severity, String summary, String detail) {
        FacesMessage message = new FacesMessage( severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
