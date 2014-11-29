/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Embeddable
public class FormulaValuePK implements Serializable {

    @Column(name = "formula_value_id", nullable = false)
    private Short formulaValueId;
    @Column(name = "formula_id", nullable = false)
    private Short formulaId;
    @Column(name = "property_id", nullable = false)
    private Short propertyId;

    public FormulaValuePK() {
    }

    public FormulaValuePK(Short formulaValueId, Short formulaId, Short propertyId) {
        this.formulaValueId = formulaValueId;
        this.formulaId = formulaId;
        this.propertyId = propertyId;
    }

    public Short getFormulaValueId() {
        return formulaValueId;
    }

    public void setFormulaValueId(Short formulaValueId) {
        this.formulaValueId = formulaValueId;
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
        hash += (formulaValueId == null ? 0 : formulaValueId.hashCode());
        hash += (formulaId == null ? 0 : formulaId.hashCode());
        hash += (propertyId == null ? 0 : propertyId.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormulaValuePK)) {
            return false;
        }
        FormulaValuePK other = (FormulaValuePK) object;
        if ((this.formulaValueId == null && other.formulaValueId != null) || (this.formulaValueId != null && !this.formulaValueId.equals(other.formulaValueId))) {
            return false;
        }
        if ((this.formulaId == null && other.formulaId != null) || (this.formulaId != null && !this.formulaId.equals(other.formulaId))) {
            return false;
        }
        if ((this.propertyId == null && other.propertyId != null) || (this.propertyId != null && !this.propertyId.equals(other.propertyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.FormulaValuePK[ formulaValueId=" + formulaValueId + ", formulaId=" + formulaId + ", propertyId=" + propertyId + " ]";
    }

}
