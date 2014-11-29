/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "property", catalog = "", schema = "")
@XmlRootElement
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "propertySeqGenerator", sequenceName = "SEQ_PROPERTY", allocationSize = 1)
    @GeneratedValue(generator = "propertySeqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "property_id", nullable = false)
    private Short propertyId;
    @Basic(optional = false)
    @Column(name = "property_code", nullable = false, length = 50)
    private String propertyCode;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String description;
    @OneToMany(mappedBy = "property")
    private List<PropertyCode> propertyCodeList;
    @OneToMany(mappedBy = "property")
    private List<PropertyValue> propertyValueList;
    @OneToMany(mappedBy = "property")
    private List<FormulaProperty> formulaPropertyList;

    public Property() {
    }

    public Property(Short propertyId) {
        this.propertyId = propertyId;
    }

    public Property(Short propertyId, String propertyCode, String description) {
        this.propertyId = propertyId;
        this.propertyCode = propertyCode;
        this.description = description;
    }

    @PrePersist
    @PreUpdate
    private void prePersistUpdate() {
        if (propertyValueList != null) {
            for (PropertyValue propertyValue : propertyValueList) {
                propertyValue.setProperty(this);
            }
        }
    }

    public Short getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Short propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

    public void setPropertyValueList(List<PropertyValue> propertyValueList) {
        this.propertyValueList = propertyValueList;
    }

    @XmlTransient
    public List<PropertyCode> getPropertyCodeList() {
        return propertyCodeList;
    }

    public void setPropertyCodeList(List<PropertyCode> propertyCodeList) {
        this.propertyCodeList = propertyCodeList;
    }

    public List<FormulaProperty> getFormulaPropertyList() {
        return formulaPropertyList;
    }

    public void setFormulaPropertyList(List<FormulaProperty> formulaPropertyList) {
        this.formulaPropertyList = formulaPropertyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propertyId != null ? propertyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Property)) {
            return false;
        }
        Property other = (Property) object;
        if ((this.propertyId == null && other.propertyId != null) || (this.propertyId != null && !this.propertyId.equals(other.propertyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.Property[ propertyId=" + propertyId + " ]";
    }

}
