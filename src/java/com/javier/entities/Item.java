/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author CAROLINA
 */
@Entity
@Table(name = "items")
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManyToMany
    private List<Sale> sales;
    @ManyToMany
    private List<Purchase> purchases;
    
    public Item() {
    }  
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false, length = 384)
    private String name;
    
    @Column(nullable = false, name = "sale_price", precision = 2)
    private Double salePrice ;
    
    @Column(nullable = false, name = "buy_price", precision = 2)
    private Double buyPrice ;
    
    @Column(nullable = true, name = "image_url" )
    private String mainImageUrl ;
    
    @Column(nullable = true, name = "external_id")
    private String externalId ;
    
    @Column(nullable = true)
    private int inventory ;
    
    @Column(nullable = true)
    private int stock ;

    public Item(List<Sale> sales, List<Purchase> purchases, String name, Double salePrice, Double buyPrice, String mainImageUrl, String externalId, int inventory, int stock) {
        this.sales = sales;
        this.purchases = purchases;
        this.name = name;
        this.salePrice = salePrice;
        this.buyPrice = buyPrice;
        this.mainImageUrl = mainImageUrl;
        this.externalId = externalId;
        this.inventory = inventory;
        this.stock = stock;
    }        

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }
    
    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }    

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javier.entities.Item[ id=" + id + " ]";
    }
    
}
