/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Embeddable
public class FormulaPropertyPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "formula_id", nullable = false)
    private Short formulaId;
    @Basic(optional = false)
    @Column(name = "property_id", nullable = false)
    private Short propertyId;

    public FormulaPropertyPK() {
    }

    public FormulaPropertyPK(Short formulaId, Short propertyId) {
        this.formulaId = formulaId;
        this.propertyId = propertyId;
    }

    public Short getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(Short formulaId) {
        this.formulaId = formulaId;
    }

    public Short getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Short propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) formulaId;
        hash += (int) propertyId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormulaPropertyPK)) {
            return false;
        }
        FormulaPropertyPK other = (FormulaPropertyPK) object;
        if (this.formulaId != other.formulaId) {
            return false;
        }
        if (this.propertyId != other.propertyId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.FormulaPropertyPK[ formulaId=" + formulaId + ", propertyId=" + propertyId + " ]";
    }
    
}
