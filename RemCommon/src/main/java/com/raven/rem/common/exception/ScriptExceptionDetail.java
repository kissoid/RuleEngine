/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.common.exception;

import java.io.Serializable;


/**
 *
 * @author Mehmet Adem Sengul
 */
public class ScriptExceptionDetail implements Serializable {

    private Integer exceptionDetailNo;
    private String fileName;
    private String className;
    private String methodName;
    private Integer lineNumber;
    private ScriptException exceptionStackTrace;

    public ScriptExceptionDetail() {
    }

    public Integer getExceptionDetailNo() {
        return exceptionDetailNo;
    }

    public void setExceptionDetailNo(Integer exceptionDetailNo) {
        this.exceptionDetailNo = exceptionDetailNo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public ScriptException getExceptionStackTrace() {
        return exceptionStackTrace;
    }

    public void setExceptionStackTrace(ScriptException exceptionStackTrace) {
        this.exceptionStackTrace = exceptionStackTrace;
    }
    
}
