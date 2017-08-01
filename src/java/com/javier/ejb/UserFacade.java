/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javier.ejb;

import com.javier.entities.User;
import com.javier.utils.Crypter;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author CAROLINA
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "StorePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public boolean checkUserLogin(String username, String password)
    {
        try {
        // crypt password
        String pass = Crypter.cryptMD5(password);
        Query query = em.createNativeQuery("select u.* FROM users u where u.username = '"+
                                    username+"' and u.password = '"+pass+"'", User.class);
        User user = (User)query.getSingleResult();
        
        return ( user != null ) ;            
        } catch (NoResultException e) {
            e.printStackTrace();
            return false ;
        }
    }
    
}
