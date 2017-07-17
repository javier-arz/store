/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.ejb.ItemFacade;
import com.javier.entities.Item;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author CAROLINA
 */
@ManagedBean(name = "itemMB")
@RequestScoped
public class ItemMB {

    @EJB
    private ItemFacade itemFacade;

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
        itemFacade.create(item);
        itemsList = listItems();
        return "welcomePrimefaces";
    }
}
