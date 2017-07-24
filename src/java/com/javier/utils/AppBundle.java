/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.utils;

import java.util.ResourceBundle;

/**
 *
 * @author CAROLINA
 */
public final class AppBundle {
    
    private static final ResourceBundle bundle = ResourceBundle.getBundle("com.javier.texts.messages");
    
    public static String getTextMessage(String text)
    {
        if ( text == null || text.isEmpty() ) {
            return "No valid text was provided" ;
        }
        try {
            String msg = bundle.getString(text);
            return msg ;
        } catch (Exception e) {
            e.printStackTrace();
            return "No available message" ;
        }
    }
    
}
