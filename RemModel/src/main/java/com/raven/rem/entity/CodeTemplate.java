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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Entity
@Table(name = "code_template", catalog = "", schema = "")
@XmlRootElement
public class CodeTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "codeTemplateSeqGenerator", sequenceName = "SEQ_CODE_TEMPLATE", allocationSize = 1)
    @GeneratedValue(generator = "codeTemplateSeqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "code_template_id", nullable = false)
    private Integer codeTemplateId;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String description;
    @Basic(optional = false)
    @Column(name = "code_text", nullable = false, length = 1000)
    private String codeText;

    public CodeTemplate() {
    }

    public CodeTemplate(Integer codeTemplateId) {
        this.codeTemplateId = codeTemplateId;
    }

    public CodeTemplate(Integer codeTemplateId, String description, String codeText) {
        this.codeTemplateId = codeTemplateId;
        this.description = description;
        this.codeText = codeText;
    }

    public Integer getCodeTemplateId() {
        return codeTemplateId;
    }

    public void setCodeTemplateId(Integer codeTemplateId) {
        this.codeTemplateId = codeTemplateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeTemplateId != null ? codeTemplateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodeTemplate)) {
            return false;
        }
        CodeTemplate other = (CodeTemplate) object;
        if ((this.codeTemplateId == null && other.codeTemplateId != null) || (this.codeTemplateId != null && !this.codeTemplateId.equals(other.codeTemplateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.CodeTemplate[ codeTemplateId=" + codeTemplateId + " ]";
    }
    
}
