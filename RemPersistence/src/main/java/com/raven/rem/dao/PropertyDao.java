/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.dao;

import com.raven.rem.entity.Property;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Stateless
public class PropertyDao extends AbstractDao<Property> {
    @PersistenceContext(unitName = "iremPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PropertyDao() {
        super(Property.class);
    }
    
}
