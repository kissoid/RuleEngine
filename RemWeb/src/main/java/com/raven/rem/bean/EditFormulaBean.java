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
@ManagedBean(name = "editFormulaBean")
@SessionScoped
public class EditFormulaBean implements Serializable {

    @EJB
    private FormulaService formulaService;
	private Formula newFormula;
	private Formula selectedFormula;
	private List<Formula> formulaList;
    
    
    /**
     * Creates a new instance of EditorBean
     */
	public EditFormulaBean() {
		newFormula = new Formula();
    }

    @PostConstruct
    private void init(){
        retrieveFormulas();
    }
    
    public void retrieveFormulas(){
        try {
			formulaList = formulaService.retrieveFormulas();
        } catch (Exception ex) {
			Logger.getLogger(EditFormulaBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editSelected() {
		newFormula = selectedFormula;
    }

	public void clear() {
		newFormula = new Formula();
	}

    public void saveFormula(){
        try {
			formulaService.saveFormula(newFormula);
			newFormula = new Formula();
            retrieveFormulas();
			FacesUtil.createFacesMessage("Info", "Formula saved", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
			Logger.getLogger(EditFormulaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	/*public void createPropertyValueCartesian(Formula formula) {
	 Map<Short, Integer> propertyValuePosition = new HashMap<Short, Integer>();
	 for (FormulaProperty formulaProperty : formula.getIremFormulaPropertyList()) {
	 Property property = formulaProperty.getProperty();
	 propertyValuePosition.put(property.getIremPropertyId(), 0);
	 }
			
	 for (Entry entry : propertyValuePosition.entrySet()) {
	 Short propertyId = (Short)entry.getKey();
	 Integer valuePosition = (Integer)entry.getValue();
			
	 Property property = findPropertyInList(propertyId);
	 for()
	 }
	 }*/
	private Property findPropertyInList(Short propertyId, Formula formula) {
		for (FormulaProperty formulaProperty : formula.getFormulaPropertyList()) {
			Property property = formulaProperty.getProperty();
			if (property.getPropertyId().equals(propertyId)) {
				return property;
			}
		}
		return null;
	}
}
