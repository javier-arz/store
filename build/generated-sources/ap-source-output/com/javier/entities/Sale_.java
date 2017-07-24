package com.javier.entities;

import com.javier.entities.Item;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-24T09:54:02")
@StaticMetamodel(Sale.class)
public class Sale_ { 

    public static volatile SingularAttribute<Sale, Calendar> date;
    public static volatile SingularAttribute<Sale, Long> id;
    public static volatile ListAttribute<Sale, Item> items;

}