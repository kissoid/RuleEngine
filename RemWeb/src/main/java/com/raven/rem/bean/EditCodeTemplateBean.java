/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.bean;

import com.raven.rem.common.util.FacesUtil;
import com.raven.rem.entity.CodeTemplate;
import com.raven.rem.service.CodeTemplateService;
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
@ManagedBean(name = "editCodeTemplateBean")
@SessionScoped
public class EditCodeTemplateBean implements Serializable {

    @EJB
    private CodeTemplateService codeTemplateService;
    @ManagedProperty("#{groovyEditorBean}")
    private GroovyEditorBean groovyEditorBean;
    private CodeTemplate newCodeTemplate;
    private CodeTemplate selectedCodeTemplate;
    private List<CodeTemplate> codeTemplateList;

    /**
     * Creates a new instance of EditorBean
     */
    public EditCodeTemplateBean() {
        newCodeTemplate = new CodeTemplate();
    }

    @PostConstruct
    private void init() {
        retrieveCodeTemplates();
    }

    public void retrieveCodeTemplates() {
        try {
            codeTemplateList = codeTemplateService.retrieveCodeTemplates();
        } catch (Exception ex) {
            Logger.getLogger(EditCodeTemplateBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editSelected() {
        newCodeTemplate = selectedCodeTemplate;
    }

	public void clear() {
		newCodeTemplate = new CodeTemplate();
	}

    public void saveCodeTemplate() {
        try {
            codeTemplateService.saveCodeTemplate(newCodeTemplate);
            newCodeTemplate = new CodeTemplate();
            retrieveCodeTemplates();
            groovyEditorBean.retriveCodeTemplates();
            FacesUtil.createFacesMessage("Info", "Code template saved", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            Logger.getLogger(EditCodeTemplateBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
