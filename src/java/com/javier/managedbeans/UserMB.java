/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.ejb.UserFacade;
import com.javier.entities.User;
import com.javier.utils.AppBundle;
import com.javier.utils.AppFacesContext;
import com.javier.utils.AppMessages;
import com.javier.utils.Crypter;
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

    public static final String OPERATION_UPDATE = "Update";

    public static final String OPERATION_CREATE = "Create";

    public static final String USERS_VIEW_CREATE = "usersCreate";

    public static final String USERS_VIEW_INDEX = "users";

    // Element to receive the file images (to upload)
    private Part imageFile;

    // For the image to download ...
    private StreamedContent imageToDownload;

    private User user;

    private List<User> usersList = new ArrayList();

    private String passwordConfirmation;

    private String currentOperation;

    /**
     * Creates a new instance of UserMB
     */
    public UserMB() {
        this.user = new User();
        this.currentOperation = OPERATION_CREATE;
        // initialize image folders
        // Check if folder exists
        if (!Files.exists(Paths.get(USER_IMG_UPLOAD_FOLDER))) {
            File dir = new File(USER_IMG_UPLOAD_FOLDER);
            dir.mkdirs();
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

    public String getCurrentOperation() {
        return currentOperation;
    }

    public void setCurrentOperation(String currentOperation) {
        this.currentOperation = currentOperation;
    }

    public String saveUser() {

        if (currentOperation == OPERATION_UPDATE) {
            return updateUser();
        }

        if (imageFile != null) {
            saveImage();
        }

        // crypt password
        user.setPassword(Crypter.cryptMD5(user.getPassword()));

        userFacade.create(user);
        usersList = listUsers();
        return USERS_VIEW_INDEX;
    }

    public String updateUser() {
        if (imageFile != null) {
            updateImage();
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            // Find the current password and leave it the same
            User currentUser = userFacade.find(user.getId());
            if (currentUser != null) {
                user.setPassword(currentUser.getPassword());
            }
        } else {
            // crypt password
            user.setPassword(Crypter.cryptMD5(user.getPassword()));
        }

        userFacade.edit(user);
        usersList = listUsers();
        return USERS_VIEW_INDEX;
    }

    protected void saveImage() {
        try {
            InputStream in = imageFile.getInputStream();
            String fileName = USER_IMG_UPLOAD_FOLDER + user.getUsername() + "_img_"
                    + imageFile.getSubmittedFileName();
            System.out.println(fileName);
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

    protected void updateImage() {
        //check if imageFile exists
        if (user.getUserImageUrl() != null && !user.getUserImageUrl().isEmpty()
                && Files.exists(Paths.get(user.getUserImageUrl()))) {
            File file = new File(user.getUserImageUrl());
            file.delete();
        }
        saveImage();
    }

    public List<User> listUsers() {
        return userFacade.findAll();
    }

    public int countUsers() {
        return userFacade.count();
    }

    public String userIndex() {
        if (usersList == null || usersList.size() == 0) {
            usersList = listUsers();
        }
        return USERS_VIEW_INDEX;
    }

    public String userCreate() {
        // Clear the item object
        user = new User();
        currentOperation = OPERATION_CREATE;
        return USERS_VIEW_CREATE;
    }

    public boolean checkUserLogin(String username, String password) {
        return userFacade.checkUserLogin(username, password);
    }

    public String userUpdate(User user_) {
        currentOperation = OPERATION_UPDATE;
        user = user_;
       
        AppMessages.addMesage(FacesMessage.SEVERITY_INFO, AppBundle.getTextMessage("MSG_TITLE_DELETION"), 
                AppBundle.getTextMessage("MSG_TEXT_USER_DELETION"));
    
        return USERS_VIEW_CREATE;
    }

    public void userDelete(User user_) {
        userFacade.remove(user_);
        usersList = listUsers();
       
        AppMessages.addMesage(FacesMessage.SEVERITY_INFO, AppBundle.getTextMessage("MSG_TITLE_DELETION"), 
                AppBundle.getTextMessage("MSG_TEXT_USER_DELETION"));
    }

    public boolean checkIfCreate() {
        return (currentOperation.equals(OPERATION_CREATE));
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
