/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.ejb.UserFacade;
import com.javier.entities.User;
import com.javier.utils.AppFacesContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.Part;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author CAROLINA
 */
@ManagedBean(name = "userMB")
@SessionScoped
public class UserMB {

    @EJB
    private UserFacade userFacade;

    public static final String USER_IMG_UPLOAD_FOLDER = AppFacesContext.getUploadImageLocation() + "users/";

    public static final String DEFAULT_IMAGE = AppFacesContext.getDefaultImageLocation();

    // Element to receive the file images (to upload)
    private Part imageFile;

    // For the image to download ...
    private StreamedContent imageToDownload;

    private User user;

    private List<User> usersList = new ArrayList();
    
    private String passwordConfirmation ;

    /**
     * Creates a new instance of UserMB
     */
    public UserMB() {
        this.user = new User();
        // initialize image folders
        // Check if folder exists
        if (Files.exists(Paths.get(USER_IMG_UPLOAD_FOLDER))) {
            try {
                Files.createDirectories(Paths.get(USER_IMG_UPLOAD_FOLDER));
            } catch (IOException ex) {
                Logger.getLogger(UserMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(Part imageFile) {
        this.imageFile = imageFile;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> aUsersList) {
        usersList = aUsersList;
    }

    public StreamedContent getImageToDownload() throws FileNotFoundException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();

            if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
                // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
                return new DefaultStreamedContent();
            } else {
                // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
                String filename = context.getExternalContext()
                        .getRequestParameterMap()
                        .get("filename");

                return new DefaultStreamedContent(new FileInputStream(new File(filename)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new DefaultStreamedContent(new FileInputStream(new File(DEFAULT_IMAGE)));
        }
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String saveUser() {
        if (imageFile != null) {
            saveImage();
        }

        userFacade.create(user);
        usersList = listUsers();
        return "users";
    }

    protected void saveImage() {
        try {
            InputStream in = imageFile.getInputStream();
            String fileName = USER_IMG_UPLOAD_FOLDER + user.getUsername() + "_img_"
                    + imageFile.getSubmittedFileName();
            File f = new File(fileName);
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            byte[] buffer = new byte[1024];

            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            user.setUserImageUrl(fileName);

            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> listUsers() {
        return userFacade.findAll();
    }

    public int countUsers() {
        return userFacade.count();
    }

    public String userIndex() {
        return "users";
    }

    public String userCreate() {
        // Clear the item object
        user = new User();
        return "usersCreate";
    }
    
        /*
     Messages
     */
    public void info() {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
    }

    public void warn() {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Watch out for PrimeFaces."));
    }

    public void error() {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
    }

    public void fatal() {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error"));
    }
}
