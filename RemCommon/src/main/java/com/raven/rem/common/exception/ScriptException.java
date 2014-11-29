/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.common.exception;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mehmet Adem Sengul
 */
public class ScriptException implements Serializable {

    private String description;
    private Date exceptionDate;
    private List<ScriptExceptionDetail> scriptExceptionDetailList;

    public ScriptException() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExceptionDate() {
        return exceptionDate;
    }

    public void setExceptionDate(Date exceptionDate) {
        this.exceptionDate = exceptionDate;
    }

    public List<ScriptExceptionDetail> getScriptExceptionDetailList() {
        return scriptExceptionDetailList;
    }

    public void setScriptExceptionDetailList(List<ScriptExceptionDetail> scriptExceptionDetailList) {
        this.scriptExceptionDetailList = scriptExceptionDetailList;
    }


}
