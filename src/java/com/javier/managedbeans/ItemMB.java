/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.ejb.ItemFacade;
import com.javier.entities.Item;
import com.javier.utils.AppBundle;
import com.javier.utils.AppFacesContext;
import com.javier.utils.AppMessages;
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
@ManagedBean(name = "itemMB")
@SessionScoped
public class ItemMB {

    @EJB
    private ItemFacade itemFacade;

    public static final String ITEM_IMG_UPLOAD_FOLDER = AppFacesContext.getUploadImageLocation() + "items/";

    public static final String DEFAULT_IMAGE = AppFacesContext.getDefaultImageLocation();
    
    public static final String OPERATION_CREATE = "Create" ;

    public static final String OPERATION_UPDATE = "Update" ;
    
    public static final String ITEMS_VIEW_LIST = "items" ;
    
    public static final String ITEMS_VIEW_CREATE = "itemsCreate" ;
    
    // Element to receive the file images (to upload)
    private Part imageFile;

    // For the image to download ...
    private StreamedContent imageToDownload;

    private Item item;
    
    private String currentOperation ;
    
    private List<Item> itemsList = new ArrayList();

    public List<Item> getItemsList() {
        return itemsList;
    }

    /**
     * Creates a new instance of ItemMB
     */
    public ItemMB() {
        this.item = new Item();
        // initialize image folders
            // Check if folder exists
            if ( !Files.exists( Paths.get(ITEM_IMG_UPLOAD_FOLDER) ) ) {
            try {
                Files.createDirectories( Paths.get(ITEM_IMG_UPLOAD_FOLDER) ) ;
            } catch (IOException ex) {
                Logger.getLogger(UserMB.class.getName()).log(Level.SEVERE, null, ex);
            }
            }  
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Part getImageFile() {
        return imageFile;
    }

    public void setImageFile(Part file) {
        this.imageFile = file;
    }

    // Primefaces method for graphic Image
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

    public String getCurrentOperation() {
        return currentOperation;
    }

    public void setCurrentOperation(String currentOperation) {
        this.currentOperation = currentOperation;
    }

    public List<Item> listItems() {
        return itemFacade.findAll();
    }

    public int countItems() {
        return itemFacade.findAll().size();
    }

    public Item find(Long itemId) {
        return itemFacade.find(itemId);
    }

    public String saveItem() {
        
        if ( getCurrentOperation() == OPERATION_UPDATE ) {
            return updateItem();
        }
        
        if (imageFile != null) {
            saveImage();
        }

        itemFacade.create(item);
        itemsList = listItems();
        AppMessages.addMesage(FacesMessage.SEVERITY_INFO, AppBundle.getTextMessage("MSG_TITLE_CREATION"), 
                AppBundle.getTextMessage("MSG_TEXT_ITEM_CREATION"));
        return ITEMS_VIEW_LIST ;
    }

    protected void saveImage() {
        try {
            InputStream in = imageFile.getInputStream();
            String fileName = ITEM_IMG_UPLOAD_FOLDER + item.getName() + "_img_"
                    + imageFile.getSubmittedFileName();
            File f = new File(fileName);
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            byte[] buffer = new byte[1024];

            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            item.setMainImageUrl(fileName);

            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String itemIndex() {
        if (itemsList == null || itemsList.size() == 0) {
            itemsList = listItems();
        }
        return ITEMS_VIEW_LIST ;
    }

    public String itemCreate() {
        // Clear the item object
        item = new Item();
        setCurrentOperation(OPERATION_CREATE);
        return ITEMS_VIEW_CREATE;
    }
    
    public String itemUpdate(Item item_) {
        item = item_ ;
        setCurrentOperation(OPERATION_UPDATE);
        return ITEMS_VIEW_CREATE ;
    }
    
    public String updateItem() {
        if ( imageFile != null ) {
            updateImage();
        }
        
        itemFacade.edit(item);
        
        itemsList = listItems();
        
        AppMessages.addMesage(FacesMessage.SEVERITY_INFO, AppBundle.getTextMessage("MSG_TITLE_UPDATING"), 
                AppBundle.getTextMessage("MSG_TEXT_ITEM_UPDATING"));
        
        return ITEMS_VIEW_LIST ;
    }
    
    protected void updateImage()
    {
        if ( item.getMainImageUrl() != null && !item.getMainImageUrl().isEmpty() 
                && Files.exists( Paths.get( item.getMainImageUrl() ) )  ) {
            File file = new File(item.getMainImageUrl()) ;
            file.delete();
        }
        saveImage();
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
