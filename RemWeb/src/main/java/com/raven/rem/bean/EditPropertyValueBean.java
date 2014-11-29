/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.bean;

import com.raven.rem.common.util.FacesUtil;
import com.raven.rem.entity.Property;
import com.raven.rem.entity.PropertyValue;
import com.raven.rem.service.PropertyService;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.SelectEvent;

/**
 *
 * Mehmet Adem Sengul
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ManagedBean(name = "editPropertyValueBean")
@SessionScoped
public class EditPropertyValueBean implements Serializable {

	@EJB
	private PropertyService propertyService;
	private Property selectedProperty;
	private Property selectedPropertyValue;
	private List<Property> propertyList;
	private PropertyValue newPropertyValue;

	/**
	 * Creates a new instance of EditorBean
	 */
	public EditPropertyValueBean() {
		newPropertyValue = new PropertyValue();
	}

	@PostConstruct
	private void init() {
		retrieveProperties();
	}

	public void retrieveProperties() {
		try {
			propertyList = propertyService.retrieveProperties();
		} catch (Exception ex) {
			Logger.getLogger(EditPropertyValueBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void editSelected(SelectEvent event) {

	}

	public void saveProperty() {
		if (selectedProperty == null) {
			return;
		}
		try {
			Short selectedPropertyId = selectedProperty.getPropertyId();
			propertyService.saveProperty(selectedProperty);
			FacesUtil.createFacesMessage("Info", "Property values saved", FacesMessage.SEVERITY_INFO);
			retrieveProperties();
			selectedProperty = findPropertyInList(selectedPropertyId);
		} catch (Exception ex) {
			Logger.getLogger(EditPropertyValueBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void addPropertyValueToProperty() {
		if (selectedProperty == null) {
			FacesUtil.createFacesMessage("Warning", "Lütefen bir property seçiniz.", FacesMessage.SEVERITY_WARN);
			return;
		}

		if (selectedProperty.getPropertyValueList() == null) {
			selectedProperty.setPropertyValueList(new ArrayList<PropertyValue>());
		}

		selectedProperty.getPropertyValueList().add(newPropertyValue);
		newPropertyValue = new PropertyValue();
	}

	public void removePropertyValueFromList(PropertyValue propertyValue) {
		selectedProperty.getPropertyValueList().remove(propertyValue);
	}

	public Property findPropertyInList(Short propertyId) {
		for (Property iremProperty : propertyList) {
			if (iremProperty.getPropertyId().equals(propertyId)) {
				return iremProperty;
			}
		}
		return null;
	}

}
