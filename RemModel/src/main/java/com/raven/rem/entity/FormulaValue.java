/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Entity
@Table(name = "formula_value", catalog = "", schema = "")
@XmlRootElement
public class FormulaValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FormulaValuePK formulaValuePK;
    @JoinColumn(name = "property_value_id", referencedColumnName = "property_value_id", nullable = false)
    @ManyToOne(optional = false)
    private PropertyValue propertyValue;
    @JoinColumns({
        @JoinColumn(name = "formula_id", referencedColumnName = "formula_id", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "property_id", referencedColumnName = "property_id", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private FormulaProperty formulaProperty;
    @JoinColumn(name = "formula_value_id", referencedColumnName = "formula_value_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private FormulaValueSeq formulaValueSeq;

    public FormulaValue() {
    }

    public FormulaValue(FormulaValuePK formulaValuePK) {
        this.formulaValuePK = formulaValuePK;
    }

    public FormulaValue(Short formulaValueId, Short formulaId, Short propertyId) {
        this.formulaValuePK = new FormulaValuePK(formulaValueId, formulaId, propertyId);
    }

    @PrePersist
    @PreUpdate
    private void prePersistUpdate() {
        if (formulaValuePK == null) {
            formulaValuePK = new FormulaValuePK();
        }
        formulaValuePK.setPropertyId(formulaProperty.getProperty().getPropertyId());
        formulaValuePK.setFormulaId(formulaValueSeq.getFormula().getFormulaId());
        formulaValuePK.setFormulaValueId(formulaValueSeq.getFormulaValueId());
    }

    public FormulaValuePK getFormulaValuePK() {
        return formulaValuePK;
    }

    public void setFormulaValuePK(FormulaValuePK formulaValuePK) {
        this.formulaValuePK = formulaValuePK;
    }

    public PropertyValue getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(PropertyValue propertyValue) {
        this.propertyValue = propertyValue;
    }

    public FormulaProperty getFormulaProperty() {
        return formulaProperty;
    }

    public void setFormulaProperty(FormulaProperty formulaProperty) {
        this.formulaProperty = formulaProperty;
    }

    public FormulaValueSeq getFormulaValueSeq() {
        return formulaValueSeq;
    }

    public void setFormulaValueSeq(FormulaValueSeq formulaValueSeq) {
        this.formulaValueSeq = formulaValueSeq;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formulaValuePK != null ? formulaValuePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormulaValue)) {
            return false;
        }
        FormulaValue other = (FormulaValue) object;
        if ((this.formulaValuePK == null && other.formulaValuePK != null) || (this.formulaValuePK != null && !this.formulaValuePK.equals(other.formulaValuePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.FormulaValue[ formulaValuePK=" + formulaValuePK + " ]";
    }

}
