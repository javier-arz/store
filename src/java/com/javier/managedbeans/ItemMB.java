/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.ejb.ItemFacade;
import com.javier.entities.Item;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author CAROLINA
 */
@ManagedBean(name = "itemMB")
@RequestScoped
public class ItemMB {

    @EJB
    private ItemFacade itemFacade;

    public static final String IMG_UPLOAD_FOLDER = FacesContext.getCurrentInstance()
            .getExternalContext()
            .getInitParameter("img-file-upload");

    public static final String ITEM_IMG_UPLOAD_FOLDER = IMG_UPLOAD_FOLDER + "items/";

    // Element to receive the file images
    private Part imageFile;

    private Item item;

    private static List<Item> itemsList = new ArrayList();

    public List<Item> getItemsList() {
        return itemsList;
    }

    /**
     * Creates a new instance of ItemMB
     */
    public ItemMB() {
        this.item = new Item();
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
        if (imageFile != null) {
            saveImage();
        }

        itemFacade.create(item);
        itemsList = listItems();
        return "welcomePrimefaces";

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
    
    public String itemIndex()
    {
        return "welcomePrimefaces";
    }
    
    public String itemCreate()
    {
        return "index";
    }
    /*
     Messages
     */
    public void info() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
    }

    public void warn() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Watch out for PrimeFaces."));
    }

    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contact admin."));
    }

    public void fatal() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error"));
    }
}
