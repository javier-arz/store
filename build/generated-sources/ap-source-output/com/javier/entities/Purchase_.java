package com.javier.entities;

import com.javier.entities.Item;
import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-19T16:00:47")
@StaticMetamodel(Purchase.class)
public class Purchase_ { 

    public static volatile SingularAttribute<Purchase, Calendar> date;
    public static volatile SingularAttribute<Purchase, Long> id;
    public static volatile ListAttribute<Purchase, Item> items;

}