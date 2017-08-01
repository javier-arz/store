/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.entities.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author CAROLINA
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    @ManagedProperty(value = "#{ itemMB }")
    private ItemMB itemMB;

    @ManagedProperty(value = "#{ userMB }")
    private UserMB userMB;

    // User of the current session
    private User user;

    private boolean isLogged;

    /**
     * Creates a new instance of loginBean
     */
    public LoginBean() {
        this.user = new User();
        this.isLogged = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ItemMB getItemMB() {
        return itemMB;
    }

    public void setItemMB(ItemMB itemMB) {
        this.itemMB = itemMB;
    }

    public UserMB getUserMB() {
        return userMB;
    }

    public void setUserMB(UserMB userMB) {
        this.userMB = userMB;
    }

    public boolean isIsLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public String login() {
        String view;
        // Hardcoded for fast first login
        if (user.getUsername() != null && user.getUsername().equals("admin") && user.getPassword() != null
                && user.getPassword().equals("admin")) {
            isLogged = true;
            user = new User() ;
            view = itemMB.itemIndex();
        } else {
            boolean isValid = userMB.checkUserLogin(user.getUsername(), user.getPassword());
            if (isValid) {
                isLogged = true;
                view = itemMB.itemIndex();
            } else {
                user = new User();
                isLogged = false;
                view = "index";
            }

        }
        return view;
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        isLogged = false;
        return "index" ;
    }

}
