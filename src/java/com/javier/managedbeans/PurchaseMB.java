/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.ejb.PurchaseFacade;
import com.javier.entities.Purchase;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author CAROLINA
 */
@ManagedBean(name = "purchaseMB")
@RequestScoped
public class PurchaseMB {
    @EJB
    private PurchaseFacade purchaseFacade;
    
    private Purchase purchase ;
    
    private static List<Purchase> purchasesList = new ArrayList() ;

    /**
     * Creates a new instance of PurchaseMB
     */    
    public PurchaseMB() {
        this.purchase = new Purchase();
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public static List<Purchase> getPurchasesList() {
        return purchasesList;
    }

    public static void setPurchasesList(List<Purchase> aPurchasesList) {
        purchasesList = aPurchasesList;
    }
    
    public String purchaseIndex()
    {
        return "purchases" ;
    }
    
}
