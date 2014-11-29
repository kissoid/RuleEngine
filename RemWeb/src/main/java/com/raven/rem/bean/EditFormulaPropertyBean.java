/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.bean;

import com.raven.rem.common.util.FacesUtil;
import com.raven.rem.entity.Formula;
import com.raven.rem.entity.FormulaProperty;
import com.raven.rem.entity.Property;
import com.raven.rem.service.FormulaService;
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
import org.primefaces.model.DualListModel;

/**
 *
 * Mehmet Adem Sengul
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ManagedBean(name = "editFormulaPropertyBean")
@SessionScoped
public class EditFormulaPropertyBean implements Serializable {

	@EJB
	private FormulaService formulaService;
	@EJB
	private PropertyService propertyService;
	private Formula selectedFormula;
	private List<Formula> formulaList;
	private DualListModel<Property> propertyDualList;
	private List<FormulaProperty> existsFormulaPropertyList;

	/**
	 * Creates a new instance of EditorBean
	 */
	public EditFormulaPropertyBean() {
		existsFormulaPropertyList = new ArrayList<FormulaProperty>();
		propertyDualList = new DualListModel<Property>();
	}

	@PostConstruct
	private void init() {
		retrieveFormulas();
	}

	public void retrieveFormulas() {
		try {
			formulaList = formulaService.retrieveFormulas();
		} catch (Exception ex) {
			Logger.getLogger(EditFormulaPropertyBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void retrieveAvaliableProperties() {
		try {
			List<Property> tempPropertyList = propertyService.retrieveProperties();
			propertyDualList.getSource().clear();
			propertyDualList.getSource().addAll(tempPropertyList);
		} catch (Exception ex) {
			Logger.getLogger(EditFormulaPropertyBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void createAllPropertyListings() {
		if (selectedFormula == null) {
			return;
		}
		propertyDualList.getTarget().clear();
		existsFormulaPropertyList.clear();
		for (FormulaProperty formulaProperty : selectedFormula.getFormulaPropertyList()) {
			propertyDualList.getTarget().add(formulaProperty.getProperty());
			propertyDualList.getSource().remove(formulaProperty.getProperty());
			existsFormulaPropertyList.add(formulaProperty);
		}
	}

	public void editSelected() {
		retrieveAvaliableProperties();
		createAllPropertyListings();
	}

	public void saveFormula() {
		try {
			if (selectedFormula == null) {
				FacesUtil.createFacesMessage("Warning", "Please select a formula", FacesMessage.SEVERITY_WARN);
				return;
			}
			createFormulaProperties();
			formulaService.saveFormulaAndCreateFormulaValues(selectedFormula);
			FacesUtil.createFacesMessage("Info", "Formula property saved", FacesMessage.SEVERITY_INFO);
		} catch (Exception ex) {
			Logger.getLogger(EditFormulaPropertyBean.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void createFormulaProperties() {
		List<FormulaProperty> formulaPropertylist = new ArrayList<FormulaProperty>();
		for (Property iremProperty : propertyDualList.getTarget()) {
			FormulaProperty iremFormulaProperty = findInExistingFormulaProperties(iremProperty);
			if (iremFormulaProperty == null) {
				iremFormulaProperty = new FormulaProperty();
				iremFormulaProperty.setProperty(iremProperty);
			}
			formulaPropertylist.add(iremFormulaProperty);
		}
		selectedFormula.setFormulaPropertyList(formulaPropertylist);
	}

	private FormulaProperty findInExistingFormulaProperties(Property property) {
		for (FormulaProperty formulaProperty : existsFormulaPropertyList) {
			if (formulaProperty.getProperty().getPropertyId().equals(property.getPropertyId())) {
				return formulaProperty;
			}
		}
		return null;
	}
}
