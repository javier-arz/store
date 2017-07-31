/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.utils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author CAROLINA
 */
public class AppFacesContext {

    private static final ExternalContext facesContext = FacesContext.getCurrentInstance()
            .getExternalContext();

    public static String getUploadImageLocation() {
        return facesContext.getInitParameter("img-file-upload");
    }

    public static String getDefaultImageLocation()
    {
        return facesContext.getInitParameter("default-img-location") ;
    }
    
    public static int getMinPasswordSize()
    {
        return Integer.parseInt(facesContext.getInitParameter("min-password-length")) ;
    }
}
