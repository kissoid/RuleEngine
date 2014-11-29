/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.bean;

import com.raven.rem.common.util.FacesUtil;
import com.raven.rem.entity.Module;
import com.raven.rem.service.ModuleService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * Mehmet Adem Sengul
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ManagedBean(name = "editModuleBean")
@SessionScoped
public class EditModuleBean implements Serializable {

    @EJB
    private ModuleService moduleService;
    private Module newModule;
    private Module selectedModule;
    private List<Module> moduleList;
    
    
    /**
     * Creates a new instance of EditorBean
     */
    public EditModuleBean() {
        newModule = new Module();
    }

    @PostConstruct
    private void init(){
        retrieveModules();
    }
    
    public void retrieveModules(){
        try {
            moduleList = moduleService.retrieveModules();
        } catch (Exception ex) {
            Logger.getLogger(EditModuleBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editSelected() {
        newModule = selectedModule;
    }

	public void clear() {
		newModule = new Module();
	}

    public void saveModule(){
        try {
            moduleService.saveFormula(newModule);
            newModule = new Module();
            retrieveModules();
            FacesUtil.createFacesMessage("Info", "Module saved", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            Logger.getLogger(EditModuleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
