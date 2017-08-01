/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CAROLINA
 */
public class Crypter {

    public static String cryptMD5(String password) {
        MessageDigest messageDigestMD5;
        try {
            messageDigestMD5 = MessageDigest.getInstance("MD5");

            byte[] passBytes = password.getBytes();
            messageDigestMD5.reset();
            byte[] digested = messageDigestMD5.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Crypter.class.getName()).log(Level.SEVERE, null, ex);
            return password;
        }
    }
}
