/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Entity
@Table(name = "property_code", catalog = "", schema = "")
@XmlRootElement
public class PropertyCode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "propertyCodeSeqGenerator", sequenceName = "SEQ_PROPERTY_CODE", allocationSize = 1)
    @GeneratedValue(generator = "propertyCodeSeqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "property_code_id", nullable = false)
    private Integer propertyCodeId;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String code;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String description;
    @JoinColumn(name = "property_id", referencedColumnName = "property_id")
    @ManyToOne
    private Property property;

    public PropertyCode() {
    }

    public PropertyCode(Integer propertyCodeId) {
        this.propertyCodeId = propertyCodeId;
    }

    public PropertyCode(Integer propertyCodeId, String code, String description) {
        this.propertyCodeId = propertyCodeId;
        this.code = code;
        this.description = description;
    }

    public Integer getPropertyCodeId() {
        return propertyCodeId;
    }

    public void setPropertyCodeId(Integer propertyCodeId) {
        this.propertyCodeId = propertyCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propertyCodeId != null ? propertyCodeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropertyCode)) {
            return false;
        }
        PropertyCode other = (PropertyCode) object;
        if ((this.propertyCodeId == null && other.propertyCodeId != null) || (this.propertyCodeId != null && !this.propertyCodeId.equals(other.propertyCodeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.PropertyCode[ propertyCodeId=" + propertyCodeId + " ]";
    }
    
}
