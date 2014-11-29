/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.service;

import com.raven.rem.entity.ImportTemplate;
import com.raven.rem.entity.Rule;
import com.raven.rem.script.ScriptParameter;
import com.raven.rem.script.ScriptResult;
import com.raven.rem.script.ScriptResultStatus;
import com.raven.rem.script.ScriptUtil;
import groovy.lang.Binding;
import groovy.lang.Script;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author Mehmet Adem Sengul
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateful(name = "scriptExecuterService", mappedName = "scriptExecuterService")
@LocalBean
public class ScriptExecuterService {

    private static final Short DEVELOPMENT = 1;
    private static final Short PRODUCTION = 2;

    @EJB
    private ScriptService scriptService;
    @EJB
    private ScriptCacheService scriptCacheService;
    @EJB
    private ImportTemplateService importTemplateService;
    private List<ImportTemplate> defaultImportTemplateList;

    public List<ScriptResult> executeFormulaScripts(List<ScriptParameter> scriptParameters) throws Exception {
        List<Rule> ruleList = scriptService.retrieveScripts();
        Binding binding = convertScriptParameterToBinding(scriptParameters);
        List<ScriptResult> scriptResultList = new ArrayList<ScriptResult>();
        for (Rule rule : ruleList) {
            ScriptResult scriptResult = processScript(rule, binding, PRODUCTION);
            scriptResultList.add(scriptResult);
        }
        return scriptResultList;
    }

    public ScriptResult executeTestScript(Rule rule, List<ScriptParameter> scriptParameters) throws Exception {
        Binding binding = convertScriptParameterToBinding(scriptParameters);
        return processScript(rule, binding, DEVELOPMENT);
    }

    public ScriptResult processScript(Rule rule, Binding binding, Short mode) throws Exception {
        Script script = null;
        boolean scriptInCache = false;
        //Eğer script cache de varsa oradan getir
        if (rule.getRuleId() != null && mode.equals(PRODUCTION)) {
            script = scriptCacheService.getCache(rule.getRuleId());
        }
        /*Eger script cache de bulunamazsa, java class i yap ve cache ekle.*/
        if (script == null) {
            String scriptText = prepareScriptText(rule, binding);
            script = createjavaClassFromScript(scriptText);
        } else {
            scriptInCache = true;
        }
        //Eğer script cache de değilse cache ekle
        if (rule.getRuleId() != null && scriptInCache == false && mode.equals(PRODUCTION)) {
            scriptCacheService.putCache(rule.getRuleId(), script);
        }
        /*Scripti calistir*/
        Object result = executeScript(script, binding);

        ScriptResult scriptResult;
        if (result != null) {
            scriptResult = (ScriptResult) result;
        } else {
            scriptResult = new ScriptResult();
            scriptResult.setScriptStatus(ScriptResultStatus.APPROVED);
        }
        /*Rule calisirken hata olusursa veya script kontrolden gecmezse donguden cik*/
        if (scriptResult.getScriptStatus() != ScriptResultStatus.APPROVED) {
            return scriptResult;
        }

        return scriptResult;
    }

    private String prepareScriptText(Rule rule, Binding binding) throws Exception {
        StringBuilder scriptText = new StringBuilder();

        if (defaultImportTemplateList == null) {
            defaultImportTemplateList = importTemplateService.retrieveDefaultImportTemplates();
        }

        for (ImportTemplate importTemplate : defaultImportTemplateList) {
            scriptText.append(createImportLine(importTemplate));
        }

        if (rule.getImportTemplateList() != null) {
            for (ImportTemplate importTemplate : rule.getImportTemplateList()) {
                scriptText.append(createImportLine(importTemplate));
            }
            scriptText.append(createNewLine());
        }

        Iterator iterator = binding.getVariables().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            scriptText.append(createVariableLine(entry.getKey().toString()));
        }

        scriptText.append(createNewLine());

        scriptText.append(rule.getScriptText());

        return scriptText.toString();
    }

    private String createImportLine(ImportTemplate importTemplate) {
        StringBuilder scriptText = new StringBuilder();
        scriptText.append("import").append(" ");
        scriptText.append(importTemplate.getPackagePath());
        scriptText.append(".");
        scriptText.append(importTemplate.getClassName());
        scriptText.append(";");
        scriptText.append(createNewLine());
        return scriptText.toString();
    }

    private String createVariableLine(String variable) {
        StringBuilder scriptText = new StringBuilder();
        scriptText.append("def");
        scriptText.append(" ");
        scriptText.append(variable);
        scriptText.append(" = ");
        scriptText.append("binding.getVariables().get(\"");
        scriptText.append(variable);
        scriptText.append("\");");
        scriptText.append(createNewLine());
        return scriptText.toString();
    }

    private String createNewLine() {
        return System.getProperty("line.separator");
    }

    public Script createjavaClassFromScript(String scriptText) throws Exception {
        Script script = ScriptUtil.createJavaClassFromScript(scriptText);
        return script;
    }

    private ScriptResult executeScript(Script script, Binding binding) {
        ScriptResult scriptResult;
        try {
            script.setBinding(binding);
            scriptResult = (ScriptResult) script.run();
        } catch (Exception ex) {
            scriptResult = new ScriptResult();
            scriptResult.setScriptStatus(ScriptResultStatus.ERROR);
            scriptResult.setScriptMessage(ex.getMessage());
        }
        return scriptResult;
    }

    private Binding convertScriptParameterToBinding(List<ScriptParameter> scriptParameters) {
        Binding binding = new Binding();
        for (ScriptParameter scriptParameter : scriptParameters) {
            binding.getVariables().put(scriptParameter.getCode(), scriptParameter.getValue());
        }
        return binding;
    }
}
