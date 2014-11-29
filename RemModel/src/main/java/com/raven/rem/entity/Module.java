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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Entity
@Table(name = "module", catalog = "", schema = "")
@XmlRootElement
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "moduleSeqGenerator", sequenceName = "SEQ_MODULE", allocationSize = 1)
    @GeneratedValue(generator = "moduleSeqGenerator", strategy = GenerationType.AUTO)
    @Column(name = "module_id", nullable = false)
    private Integer moduleId;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String description;

    public Module() {
    }

    public Module(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Module(Integer moduleId, String description) {
        this.moduleId = moduleId;
        this.description = description;
    }

    @PrePersist
    @PreUpdate
    private void prePersistPreUpdateMethod() {

    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moduleId != null ? moduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.moduleId == null && other.moduleId != null) || (this.moduleId != null && !this.moduleId.equals(other.moduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.raven.rem.entity.Module[ moduleId=" + moduleId + " ]";
    }

}
