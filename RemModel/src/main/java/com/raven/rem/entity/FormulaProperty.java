/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Entity
@Table(name = "formula_property", catalog = "", schema = "")
@XmlRootElement
public class FormulaProperty implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FormulaPropertyPK formulaPropertyPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formulaProperty")
    private List<FormulaValue> formulaValueList;
    @JoinColumn(name = "property_id", referencedColumnName = "property_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Property property;
    @JoinColumn(name = "formula_id", referencedColumnName = "formula_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Formula formula;

    public FormulaProperty() {
    }

    public FormulaProperty(FormulaPropertyPK formulaPropertyPK) {
        this.formulaPropertyPK = formulaPropertyPK;
    }

    public FormulaProperty(Short formulaId, Short propertyId) {
        this.formulaPropertyPK = new FormulaPropertyPK(formulaId, propertyId);
    }

	@PrePersist
	@PreUpdate
	private void prePersistUpdate() {
            if (formulaValueList != null) {
                for (FormulaValue formulaValue : formulaValueList) {
                    formulaValue.setFormulaProperty(this);
			}
		}
	}

    public FormulaPropertyPK getFormulaPropertyPK() {
        return formulaPropertyPK;
    }

    public void setFormulaPropertyPK(FormulaPropertyPK formulaPropertyPK) {
        this.formulaPropertyPK = formulaPropertyPK;
    }

    @XmlTransient
    public List<FormulaValue> getFormulaValueList() {
        return formulaValueList;
    }

    public void setFormulaValueList(List<FormulaValue> formulaValueList) {
        this.formulaValueList = formulaValueList;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formulaPropertyPK != null ? formulaPropertyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormulaProperty)) {
            return false;
        }
        FormulaProperty other = (FormulaProperty) object;
        if ((this.formulaPropertyPK == null && other.formulaPropertyPK != null) || (this.formulaPropertyPK != null && !this.formulaPropertyPK.equals(other.formulaPropertyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.FormulaProperty[ formulaPropertyPK=" + formulaPropertyPK + " ]";
    }
    
}
