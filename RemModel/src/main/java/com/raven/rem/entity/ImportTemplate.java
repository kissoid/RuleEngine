/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.entity;

import com.raven.rem.converter.BooleanConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "import_template", catalog = "", schema = "")
@XmlRootElement
public class ImportTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "importTemplateSeqGenerator", sequenceName = "SEQ_IMPORT_TEMPLATE", allocationSize = 1)
    @GeneratedValue(generator = "importTemplateSeqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "import_template_id", nullable = false)
    private Integer importTemplateId;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String description;
    @Basic(optional = false)
    @Column(name = "class_name", nullable = false, length = 100)
    private String className;
    @Column(name = "package_path", length = 255)
    private String packagePath;
    @Convert(converter = BooleanConverter.class)
    @Column(name = "is_default")
    private Boolean isDefault;
    @ManyToMany(mappedBy = "importTemplateList")
    private List<Rule> scriptList;

    public ImportTemplate() {
    }

    public ImportTemplate(Integer importTemplateId) {
        this.importTemplateId = importTemplateId;
    }

    public ImportTemplate(Integer importTemplateId, String description, String className) {
        this.importTemplateId = importTemplateId;
        this.description = description;
        this.className = className;
    }

    @PrePersist
    @PreUpdate
    private void prePersistPreUpdateMethod() {
        if (isDefault == null) {
            isDefault = false;
        }
        if (scriptList != null) {
            for (Rule script : scriptList) {
                if (script.getImportTemplateList() == null) {
                    script.setImportTemplateList(new ArrayList<ImportTemplate>());
                }
                script.getImportTemplateList().add(this);
            }
        }
    }

    public Integer getImportTemplateId() {
        return importTemplateId;
    }

    public void setImportTemplateId(Integer importTemplateId) {
        this.importTemplateId = importTemplateId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    @XmlTransient
    public List<Rule> getScriptList() {
        return scriptList;
    }

    public void setScriptList(List<Rule> scriptList) {
        this.scriptList = scriptList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (importTemplateId != null ? importTemplateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImportTemplate)) {
            return false;
        }
        ImportTemplate other = (ImportTemplate) object;
        if ((this.importTemplateId == null && other.importTemplateId != null) || (this.importTemplateId != null && !this.importTemplateId.equals(other.importTemplateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.ImportTemplate[ importTemplateId=" + importTemplateId + " ]";
    }

}
