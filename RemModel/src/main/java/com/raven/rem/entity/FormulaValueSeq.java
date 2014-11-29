/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.entity;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "formula_value_seq", catalog = "", schema = "")
@XmlRootElement
public class FormulaValueSeq implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "formulaValueSeqGenerator", sequenceName = "SEQ_FORMULA_VALUE", allocationSize = 1)
    @GeneratedValue(generator = "formulaValueSeqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "formula_value_id", nullable = false)
    private Short formulaValueId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formulaValueSeq")
    private List<FormulaValue> formulaValueList;
    @JoinColumn(name = "formula_id", referencedColumnName = "formula_id", nullable = false)
    @ManyToOne(optional = false)
    private Formula formula;

    public FormulaValueSeq() {
    }

    public FormulaValueSeq(Short formulaValueId) {
        this.formulaValueId = formulaValueId;
    }

    @PrePersist
    @PreUpdate
    private void prePersistUpdate() {
        if (formulaValueList != null) {
            for (FormulaValue formulaValue : formulaValueList) {
                formulaValue.setFormulaValueSeq(this);
            }
        }
    }

    public Short getFormulaValueId() {
        return formulaValueId;
    }

    public void setFormulaValueId(Short formulaValueId) {
        this.formulaValueId = formulaValueId;
    }

    @XmlTransient
    public List<FormulaValue> getFormulaValueList() {
        return formulaValueList;
    }

    public void setFormulaValueList(List<FormulaValue> formulaValueList) {
        this.formulaValueList = formulaValueList;
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
        hash += (formulaValueId != null ? formulaValueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormulaValueSeq)) {
            return false;
        }
        FormulaValueSeq other = (FormulaValueSeq) object;
        if ((this.formulaValueId == null && other.formulaValueId != null) || (this.formulaValueId != null && !this.formulaValueId.equals(other.formulaValueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.FormulaValueSeq[ formulaValueId=" + formulaValueId + " ]";
    }

}
