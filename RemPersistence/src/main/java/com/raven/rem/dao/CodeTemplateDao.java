/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.dao;

import com.raven.rem.entity.CodeTemplate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Stateless
public class CodeTemplateDao extends AbstractDao<CodeTemplate> {
    @PersistenceContext(unitName = "iremPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CodeTemplateDao() {
        super(CodeTemplate.class);
    }
    
}
