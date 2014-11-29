/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.ws;

import com.raven.rem.script.ScriptParameter;
import com.raven.rem.script.ScriptResult;
import com.raven.rem.script.exception.IremException;
import com.raven.rem.service.ScriptExecuterService;
import com.raven.rem.ws.input.ScriptExecuterInput;
import com.raven.rem.ws.output.ScriptExecuterOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Mehmet Adem Sengul
 */
@WebService(serviceName = "scriptExecuter", portName = "scriptExecuterPort")
@Stateless
public class ScriptExecuterWS {

    @EJB
    private ScriptExecuterService scriptExecuterService;

    /**
     * This is a sample web service operation
     *
     * @param inputList
     * @return
	 * @throws com.raven.rem.script.exception.IremException     */
    @WebMethod(operationName = "executeScript")
	public List<ScriptExecuterOutput> executeScript(@WebParam(name = "inputList") List<ScriptExecuterInput> inputList) throws IremException {
        List<ScriptParameter> scriptParameterList = convertServiceInputToScriptInput(inputList);
        List<ScriptResult> scriptResultList = null;
        try {
            scriptResultList = scriptExecuterService.executeFormulaScripts(scriptParameterList);
        } catch (Exception ex) {
			throw new IremException(ex);
        }
        List<ScriptExecuterOutput> scriptExecuterOutputList = convertScriptInputToServiceInput(scriptResultList);
        return scriptExecuterOutputList;
    }

    private List<ScriptParameter> convertServiceInputToScriptInput(List<ScriptExecuterInput> inputList) {
        List<ScriptParameter> scriptParameterList = new ArrayList<ScriptParameter>();
        for (ScriptExecuterInput scriptExecuterInput : inputList) {
            ScriptParameter scriptParameter = new ScriptParameter();
            scriptParameter.setCode(scriptExecuterInput.getKey());
            scriptParameter.setValue(scriptExecuterInput.getValue());
            scriptParameterList.add(scriptParameter);
        }
        return scriptParameterList;
    }

    private List<ScriptExecuterOutput> convertScriptInputToServiceInput(List<ScriptResult> resultList) {
        List<ScriptExecuterOutput> scriptExecuterOutputList = new ArrayList<ScriptExecuterOutput>();
        for (ScriptResult scriptResult : resultList) {
            ScriptExecuterOutput scriptExecuterOutput = new ScriptExecuterOutput();
            scriptExecuterOutput.setScriptStatus(scriptResult.getScriptStatus());
            scriptExecuterOutput.setScriptMessage(scriptResult.getScriptMessage());
            Iterator iterator = scriptResult.getScriptValueMap().entrySet().iterator();
            while (iterator.hasNext()) {
                Entry entry = (Entry) iterator.next();
                scriptExecuterOutput.addToValueList(entry.getKey(), entry.getValue());
            }
            
            scriptExecuterOutputList.add(scriptExecuterOutput);
        }
        return scriptExecuterOutputList;
    }
}
