/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "script", catalog = "", schema = "")
@XmlRootElement
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ruleSeqGenerator", sequenceName = "SEQ_RULE", allocationSize = 1)
    @GeneratedValue(generator = "ruleSeqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "rule_id", nullable = false)
    private Integer ruleId;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String description;
    @Basic(optional = false)
    @Column(name = "script_text", nullable = false, length = 3000)
    private String scriptText;
    @JoinTable(name = "script_import", joinColumns = {
        @JoinColumn(name = "script_id", referencedColumnName = "script_id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "import_template_id", referencedColumnName = "import_template_id", nullable = false)})
    @ManyToMany
    private List<ImportTemplate> importTemplateList;


    public Rule() {
    }

    public Rule(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Rule(Integer ruleId, String description, String scriptText) {
        this.ruleId = ruleId;
        this.description = description;
        this.scriptText = scriptText;
    }

    @PrePersist
    @PreUpdate
    private void prePersistPreUpdateMethod() {
        if (importTemplateList != null) {
            for (ImportTemplate importTemplate : importTemplateList) {
                if (importTemplate.getScriptList() == null) {
                    importTemplate.setScriptList(new ArrayList<Rule>());
                }
                importTemplate.getScriptList().add(this);
            }
        }
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScriptText() {
        return scriptText;
    }

    public void setScriptText(String scriptText) {
        this.scriptText = scriptText;
    }

    @XmlTransient
    public List<ImportTemplate> getImportTemplateList() {
        return importTemplateList;
    }

    public void setImportTemplateList(List<ImportTemplate> importTemplateList) {
        this.importTemplateList = importTemplateList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruleId != null ? ruleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rule)) {
            return false;
        }
        Rule other = (Rule) object;
        if ((this.ruleId == null && other.ruleId != null) || (this.ruleId != null && !this.ruleId.equals(other.ruleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.Script[ scriptId=" + ruleId + " ]";
    }
    
}
