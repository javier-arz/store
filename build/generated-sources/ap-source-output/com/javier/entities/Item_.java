package com.javier.entities;

import com.javier.entities.Purchase;
import com.javier.entities.Sale;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-28T12:34:55")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Double> buyPrice;
    public static volatile ListAttribute<Item, Purchase> purchases;
    public static volatile SingularAttribute<Item, Double> salePrice;
    public static volatile SingularAttribute<Item, String> name;
    public static volatile SingularAttribute<Item, String> externalId;
    public static volatile SingularAttribute<Item, Long> id;
    public static volatile SingularAttribute<Item, Integer> inventory;
    public static volatile SingularAttribute<Item, Integer> stock;
    public static volatile SingularAttribute<Item, String> mainImageUrl;
    public static volatile ListAttribute<Item, Sale> sales;

}