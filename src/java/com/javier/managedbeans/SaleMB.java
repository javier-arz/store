/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.managedbeans;

import com.javier.ejb.SaleFacade;
import com.javier.entities.Sale;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author CAROLINA
 */
@ManagedBean(name = "saleMB")
@RequestScoped
public class SaleMB {
    @EJB
    private SaleFacade saleFacade;
    
    private Sale sale ;
    
    private Sale salesList ;

    /**
     * Creates a new instance of SaleMB
     */
    public SaleMB() {
        this.sale = new Sale() ;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Sale getSalesList() {
        return salesList;
    }

    public void setSalesList(Sale salesList) {
        this.salesList = salesList;
    }
    
    public String saleIndex()
    {
        return "sales" ;
    }
    
}
