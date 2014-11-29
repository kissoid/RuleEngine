/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.service;

import com.raven.rem.dao.ImportTemplateDao;
import com.raven.rem.entity.ImportTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Stateless(name = "importTemplateService", mappedName = "importTemplateService")
@LocalBean
public class ImportTemplateService {
    
    @EJB
    private ImportTemplateDao iremImportTemplateDao;
       
    public List<ImportTemplate> retrieveAllImportTemplates() throws Exception {
        return iremImportTemplateDao.findAll();
    }

    public List<ImportTemplate> retrieveDefaultImportTemplates() throws Exception {
        Map<String, Boolean> params = new HashMap<String, Boolean>();
        params.put("isDefault", Boolean.TRUE);
        return iremImportTemplateDao.findListByNamedQuery("IremImportTemplate.findByIsDefault", params);
    }

    public List<ImportTemplate> retrieveNonDefaultImportTemplates() throws Exception {
        Map<String, Boolean> params = new HashMap<String, Boolean>();
        params.put("isDefault", Boolean.FALSE);
        return iremImportTemplateDao.findListByNamedQuery("IremImportTemplate.findByIsDefault", params);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ImportTemplate saveImportTemplate(ImportTemplate importTemplate) throws Exception {
        if (importTemplate.getImportTemplateId() == null) {
            return iremImportTemplateDao.createAndReturn(importTemplate);
        } else {
            iremImportTemplateDao.update(importTemplate);
        }
        return importTemplate;
    }    
    
}
