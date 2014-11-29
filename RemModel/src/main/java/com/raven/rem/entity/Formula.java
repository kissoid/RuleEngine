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
@Table(name = "formula", catalog = "", schema = "")
@XmlRootElement
public class Formula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@SequenceGenerator(name = "formulaSeqGenerator", sequenceName = "SEQ_FORMULA", allocationSize = 1)
	@GeneratedValue(generator = "formulaSeqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "formula_id", nullable = false)
    private Short formulaId;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formula", orphanRemoval = true)
    private List<FormulaProperty> formulaPropertyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formula")
    private List<FormulaValueSeq> formulaValueSeqList;

    public Formula() {
    }

    public Formula(Short formulaId) {
        this.formulaId = formulaId;
    }

    public Formula(Short formulaId, String description) {
        this.formulaId = formulaId;
        this.description = description;
    }

    @PrePersist
    @PreUpdate
    private void prePersistUpdate(){
        if (formulaPropertyList != null) {
            for (FormulaProperty formulaProperty : formulaPropertyList) {
                if (formulaProperty.getFormulaPropertyPK() == null) {
                    FormulaPropertyPK formulaPropertyPK = new FormulaPropertyPK();
                    formulaPropertyPK.setFormulaId(formulaId);
                    formulaPropertyPK.setPropertyId(formulaProperty.getProperty().getPropertyId());
                    formulaProperty.setFormulaPropertyPK(formulaPropertyPK);
                }
                formulaProperty.setFormula(this);
            }
        }
        if (formulaValueSeqList != null) {
            for (FormulaValueSeq formulaValueSeq : formulaValueSeqList) {
                formulaValueSeq.setFormula(this);
            }
        }
        
    }
    
    public Short getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(Short formulaId) {
        this.formulaId = formulaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<FormulaProperty> getFormulaPropertyList() {
        return formulaPropertyList;
    }

    public void setFormulaPropertyList(List<FormulaProperty> formulaPropertyList) {
        this.formulaPropertyList = formulaPropertyList;
    }

    @XmlTransient
    public List<FormulaValueSeq> getFormulaValueSeqList() {
        return formulaValueSeqList;
    }

    public void setFormulaValueSeqList(List<FormulaValueSeq> formulaValueSeqList) {
        this.formulaValueSeqList = formulaValueSeqList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formulaId != null ? formulaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formula)) {
            return false;
        }
        Formula other = (Formula) object;
        if ((this.formulaId == null && other.formulaId != null) || (this.formulaId != null && !this.formulaId.equals(other.formulaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.Formula[ formulaId=" + formulaId + " ]";
    }
    
}
