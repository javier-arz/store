/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.ejb.UserFacade;
import com.javier.entities.User;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author CAROLINA
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    @EJB
    private UserFacade userFacade;

    public static final String IMG_UPLOAD_FOLDER = FacesContext.getCurrentInstance()
            .getExternalContext()
            .getInitParameter("img-file-upload");

    public static final String USER_IMG_UPLOAD_FOLDER = IMG_UPLOAD_FOLDER + "users/";

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

    public boolean isIsLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

//    public void login(ActionEvent actionEvent) {
//        RequestContext context = RequestContext.getCurrentInstance();
//        FacesMessage msg ;
//        if (user.getUsername() != null && user.getUsername().equals("admin") && user.getPassword() != null
//                && user.getPassword().equals("admin")) {
//            isLogged = true;
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", user.getUsername());
//        } else {
//            isLogged = false;
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
//                    "Credenciales no válidas");
//        }
//
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        context.addCallbackParam("isLogged", isLogged);
//        if (isLogged) {
//            context.addCallbackParam("view", "itemsMain.xhtml");
//        }
//    }
    
        public String login() {
           String view ;
        if (user.getUsername() != null && user.getUsername().equals("admin") && user.getPassword() != null
                && user.getPassword().equals("admin")) {
            isLogged = true;
            view = "itemsMain" ;
        } else {
            isLogged = false;
            view = "index" ;
        }
        return view ;
    }

    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        isLogged = false;
    }

}
