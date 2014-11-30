/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Entity
@Table(name = "property_value", catalog = "", schema = "")
@XmlRootElement
public class PropertyValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "propertyValueSeqGenerator", sequenceName = "SEQ_PROPERTY_VALUE", allocationSize = 1)
    @GeneratedValue(generator = "propertyValueSeqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "property_value_id", nullable = false)
    private Short propertyValueId;
    @Basic(optional = false)
    @Column(name = "val_string", nullable = false, length = 250)
    private String valString;
    @JoinColumn(name = "property_id", referencedColumnName = "property_id", nullable = false)
    @ManyToOne(optional = false)
    private Property property;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propertyValue")
    private List<FormulaValue> formulaValueList;

    public PropertyValue() {
    }

    public PropertyValue(Short propertyValueId) {
        this.propertyValueId = propertyValueId;
    }

    public PropertyValue(Short propertyValueId, String valString) {
        this.propertyValueId = propertyValueId;
        this.valString = valString;
    }

    @PrePersist
    @PreUpdate
    private void prePersistUpdate() {
        if (formulaValueList != null) {
            for (FormulaValue formulaValue : formulaValueList) {
                formulaValue.setPropertyValue(this);
            }
        }
    }

    public Short getPropertyValueId() {
        return propertyValueId;
    }

    public void setPropertyValueId(Short propertyValueId) {
        this.propertyValueId = propertyValueId;
    }

    public String getValString() {
        return valString;
    }

    public void setValString(String valString) {
        this.valString = valString;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @XmlTransient
    public List<FormulaValue> getFormulaValueList() {
        return formulaValueList;
    }

    public void setFormulaValueList(List<FormulaValue> formulaValueList) {
        this.formulaValueList = formulaValueList;
    }

    public int getRowKey() {
        return this.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropertyValue)) {
            return false;
        }
        PropertyValue other = (PropertyValue) object;
        if ((this.propertyValueId == null && other.propertyValueId != null) || (this.propertyValueId != null && !this.propertyValueId.equals(other.propertyValueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.PropertyValue[ propertyValueId=" + propertyValueId + " ]";
    }

}
