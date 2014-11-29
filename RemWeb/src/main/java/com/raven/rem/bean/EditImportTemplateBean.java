/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.bean;

import com.raven.rem.common.util.FacesUtil;
import com.raven.rem.entity.ImportTemplate;
import com.raven.rem.service.ImportTemplateService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * Mehmet Adem Sengul
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ManagedBean(name = "editImportTemplateBean")
@SessionScoped
public class EditImportTemplateBean implements Serializable {

    @EJB
    private ImportTemplateService importTemplateService;
    @ManagedProperty("#{groovyEditorBean}")
    private GroovyEditorBean groovyEditorBean;
    private ImportTemplate newImportTemplate;
    private ImportTemplate selectedImportTemplate;
    private List<ImportTemplate> importTemplateList;

    /**
     * Creates a new instance of EditorBean
     */
    public EditImportTemplateBean() {
        newImportTemplate = new ImportTemplate();
    }

    @PostConstruct
    private void init() {
        retrieveImportTemplates();
    }

    public void retrieveImportTemplates() {
        try {
            importTemplateList = importTemplateService.retrieveAllImportTemplates();
        } catch (Exception ex) {
            Logger.getLogger(EditImportTemplateBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editSelected() {
        newImportTemplate = selectedImportTemplate;
    }

	public void clear() {
		newImportTemplate = new ImportTemplate();
	}

    public void saveImportTemplate() {
        try {
            importTemplateService.saveImportTemplate(newImportTemplate);
            newImportTemplate = new ImportTemplate();
            retrieveImportTemplates();
            groovyEditorBean.retriveCodeTemplates();
            FacesUtil.createFacesMessage("Info", "Import template saved", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            Logger.getLogger(EditImportTemplateBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
