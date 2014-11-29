/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.service;

import com.raven.rem.dao.CodeTemplateDao;
import com.raven.rem.dao.ModuleDao;
import com.raven.rem.entity.CodeTemplate;
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
@Stateless(name = "codeTemplateService", mappedName = "codeTemplateService")
@LocalBean
public class CodeTemplateService {
    
    @EJB
    private CodeTemplateDao iremCodeTemplateDao;
       
    public List<CodeTemplate> retrieveCodeTemplates() throws Exception {
        return iremCodeTemplateDao.findAll();
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CodeTemplate saveCodeTemplate(CodeTemplate codeTemplate) throws Exception {
        if (codeTemplate.getCodeTemplateId() == null) {
            return iremCodeTemplateDao.createAndReturn(codeTemplate);
        } else {
            iremCodeTemplateDao.update(codeTemplate);
        }
        return codeTemplate;
    }    
    
}
