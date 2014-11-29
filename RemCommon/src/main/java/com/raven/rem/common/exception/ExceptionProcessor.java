/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.common.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mehmet Adem Sengul
 */
public class ExceptionProcessor {

    public static ScriptException processException(Exception ex) {
        ScriptException scriptException = new ScriptException();
        List<ScriptExceptionDetail> stackTraceDetailList = new ArrayList<ScriptExceptionDetail>();
        scriptException.setDescription(ex.toString());
        scriptException.setExceptionDate(new Date());
        int traceIndex = 1;
        for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
            ScriptExceptionDetail scriptExceptionDetail = new ScriptExceptionDetail();
            scriptExceptionDetail.setExceptionDetailNo(traceIndex++);
            scriptExceptionDetail.setFileName(stackTraceElement.getFileName());
            scriptExceptionDetail.setClassName(stackTraceElement.getClassName());
            scriptExceptionDetail.setMethodName(stackTraceElement.getMethodName());
            scriptExceptionDetail.setLineNumber(stackTraceElement.getLineNumber());
            stackTraceDetailList.add(scriptExceptionDetail);
        }
        scriptException.setScriptExceptionDetailList(stackTraceDetailList);
        return scriptException;
    }

    public static String processExceptionAsText(Exception ex) {
        ScriptException scriptException = processException(ex);
        if (scriptException == null) {
            return "";
        }
        StringBuilder exceptionText = new StringBuilder();
        exceptionText.append(scriptException.getDescription()).append("\n");
        for (ScriptExceptionDetail scriptExceptionDetail : scriptException.getScriptExceptionDetailList()) {
            exceptionText.append("Class name  : ").append(scriptExceptionDetail.getClassName()).append(" - ");
            exceptionText.append("Method name : ").append(scriptExceptionDetail.getMethodName()).append(" - ");
            exceptionText.append("Line number : ").append(scriptExceptionDetail.getLineNumber()).append("\n");
        }
        return exceptionText.toString();
    }
}
