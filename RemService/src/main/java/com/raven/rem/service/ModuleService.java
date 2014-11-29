/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.service;

import com.raven.rem.dao.ModuleDao;
import com.raven.rem.entity.Module;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author Mehmet Adem Sengul
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless(name = "moduleService", mappedName = "moduleService")
@LocalBean
public class ModuleService {
    
    @EJB
    private ModuleDao iremModuleDao;
       
    public List<Module> retrieveModules() throws Exception {
        return iremModuleDao.findAll();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Module saveFormula(Module iremModule) throws Exception {
        if (iremModule.getModuleId() == null) {
            return iremModuleDao.createAndReturn(iremModule);
        } else {
            iremModuleDao.update(iremModule);
        }
        return iremModule;
    }    
    
}
