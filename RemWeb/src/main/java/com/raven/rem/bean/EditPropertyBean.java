/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.bean;

import com.raven.rem.common.util.FacesUtil;
import com.raven.rem.entity.Property;
import com.raven.rem.service.PropertyService;
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
@ManagedBean(name = "editPropertyBean")
@SessionScoped
public class EditPropertyBean implements Serializable {

    @EJB
    private PropertyService propertyService;
    private Property newProperty;
    private Property selectedProperty;
    private List<Property> propertyList;
    
    
    /**
     * Creates a new instance of EditorBean
     */
    public EditPropertyBean() {
        newProperty = new Property();
    }

    @PostConstruct
    private void init(){
        retrieveProperties();
    }
    
    public void retrieveProperties(){
        try {
            propertyList = propertyService.retrieveProperties();
        } catch (Exception ex) {
            Logger.getLogger(EditPropertyBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editSelected() {
        newProperty = selectedProperty;
    }

	public void clear() {
		newProperty = new Property();
	}

    public void saveProperty(){
        propertyService.saveProperty(newProperty);
        newProperty = new Property();
        retrieveProperties();
        FacesUtil.createFacesMessage("Info", "Property saved", FacesMessage.SEVERITY_INFO);
    }
}
