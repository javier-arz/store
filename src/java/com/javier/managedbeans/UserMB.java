/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.ejb.UserFacade;
import com.javier.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author CAROLINA
 */
@ManagedBean(name = "userMB")
@SessionScoped
public class UserMB {
    @EJB
    private UserFacade userFacade;
    
    private User user ;
    
    private static List<User> usersList = new ArrayList() ;

    /**
     * Creates a new instance of UserMB
     */
    public UserMB() {
        this.user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static List<User> getUsersList() {
        return usersList;
    }

    public static void setUsersList(List<User> aUsersList) {
        usersList = aUsersList;
    }
    
    public String userIndex()
    {
        return "users" ;
    }
    
}
