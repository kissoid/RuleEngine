/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.bean;

import com.raven.rem.common.exception.ExceptionProcessor;
import com.raven.rem.common.util.FacesUtil;
import com.raven.rem.entity.CodeTemplate;
import com.raven.rem.entity.ImportTemplate;
import com.raven.rem.entity.PropertyCode;
import com.raven.rem.entity.Rule;
import com.raven.rem.script.ScriptParameter;
import com.raven.rem.script.ScriptResult;
import com.raven.rem.script.ScriptResultStatus;
import com.raven.rem.service.CodeTemplateService;
import com.raven.rem.service.ImportTemplateService;
import com.raven.rem.service.PropertyService;
import com.raven.rem.service.ScriptCacheService;
import com.raven.rem.service.ScriptExecuterService;
import com.raven.rem.service.ScriptService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

/**
 *
 * Mehmet Adem Sengul
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ManagedBean(name = "groovyEditorBean")
@SessionScoped
public class GroovyEditorBean implements Serializable {

    @EJB
    private ScriptService scriptService;
    @EJB
    private ScriptExecuterService scriptExecuterService;
    @EJB
    private PropertyService propertyService;
    @EJB
    private CodeTemplateService codeTemplateService;
    @EJB
	private ImportTemplateService importTemplateService;
	@EJB
	private ScriptCacheService scriptCacheService;

    //private String editorText;
    private String resultText;
    private Rule workingCopyScript;
    private Rule selectedScript;
    private PropertyCode selectedPropertyCode;
    private CodeTemplate selectedCodeTemplate;
    private List<Rule> scriptList;
    private List<PropertyCode> propertyCodeList;
    private List<CodeTemplate> codeTemplateList;
    private List<ScriptParameter> scriptParameterList;
    private List<ImportTemplate> nonDefaultImportTemplateList;
    private DualListModel<ImportTemplate> importTemplateDualList;

    /**
     * Creates a new instance of EditorBean
     */
    public GroovyEditorBean() {
        workingCopyScript = new Rule();
        selectedScript = new Rule();
        selectedPropertyCode = new PropertyCode();
        selectedCodeTemplate = new CodeTemplate();
        importTemplateDualList = new DualListModel<ImportTemplate>();
    }

    @PostConstruct
    public void init() {
        retriveScripts();
        retriveModulePropertyCodes();
        retriveCodeTemplates();
        retriveNonDefaultImportTemplates();
        addImportTemplatesToPickList();
    }

    public void retriveModulePropertyCodes() {
        try {
            propertyCodeList = propertyService.retrievePropertyCodesByModule();
            createScriptParameters();
        } catch (Exception ex) {
            Logger.getLogger(GroovyEditorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void retriveCodeTemplates() {
        try {
            codeTemplateList = codeTemplateService.retrieveCodeTemplates();
        } catch (Exception ex) {
            Logger.getLogger(GroovyEditorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void retriveScripts() {
        try {
            scriptList = scriptService.retrieveScripts();
        } catch (Exception ex) {
            Logger.getLogger(GroovyEditorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void retriveNonDefaultImportTemplates() {
        try {
            nonDefaultImportTemplateList = importTemplateService.retrieveNonDefaultImportTemplates();
        } catch (Exception ex) {
            Logger.getLogger(GroovyEditorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveScript() {
        try {
            if (prepareScript() == false) {
                return;
            }
            if (workingCopyScript.getDescription() == null || workingCopyScript.getDescription().trim().equals("")) {
                RequestContext.getCurrentInstance().execute("PF('scriptNameWidget').show();");
                return;
            }
			workingCopyScript = scriptService.saveScript(workingCopyScript);
			scriptCacheService.removeCache(workingCopyScript.getRuleId());
            retriveScripts();
			FacesUtil.createFacesMessage("Info", "Script saved", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            Logger.getLogger(GroovyEditorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void runCode() {
        try {
            prepareScript();
            ScriptResult scriptResult = scriptExecuterService.executeTestScript(workingCopyScript, scriptParameterList);

            resultText = processScriptResult(scriptResult);
        } catch (Exception ex) {
            resultText = ExceptionProcessor.processExceptionAsText(ex);
        }
    }

    private boolean prepareScript() {
        if (workingCopyScript == null) {
            workingCopyScript = new Rule();
        }

        //workingCopyScript.setScriptText(editorText);
        createScriptImports();
        return true;
    }

    private String processScriptResult(ScriptResult scriptResult) {
        StringBuilder result = new StringBuilder();
        switch (scriptResult.getScriptStatus()) {
            case ScriptResultStatus.APPROVED:
                result.append("Script sorunsuz çalıştırıldı.").append("\n");
                result.append("Parametreler onaylandı.").append("\n");

                break;
            case ScriptResultStatus.DECLINED:
                result.append("Script sorunsuz çalıştırıldı.").append("\n");
                result.append("Parametreler onaylanmadı!!!").append("\n");
                break;
            case ScriptResultStatus.ERROR:
                result.append("Script çalıştırılırken hata oluştu").append("\n");
                break;
            default:
                result.append("Script'ten gelen mesaj anlaşılamadı.").append("\n");
        }
        result.append(scriptResult.getScriptMessage() == null ? "" : scriptResult.getScriptMessage()).append("\n");
        result.append("Dönüş değerleri").append("\n");
        result.append("===========================================================").append("\n");
        Iterator iterator = scriptResult.getScriptValueMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            result.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
        }
        result.append("===========================================================").append("\n");
        return result.toString();
    }

    private void createScriptParameters() {
        if (propertyCodeList == null) {
            return;
        }
        scriptParameterList = new ArrayList<ScriptParameter>();
        for (PropertyCode propertyCode : propertyCodeList) {
            ScriptParameter scriptParameter = new ScriptParameter();
            scriptParameter.setCode(propertyCode.getCode());
            scriptParameter.setDescription(propertyCode.getDescription());
            scriptParameterList.add(scriptParameter);
        }
    }

    private void createScriptImports() {
        if (importTemplateDualList.getTarget() == null) {
            return;
        }
        if (workingCopyScript.getImportTemplateList() == null) {
            workingCopyScript.setImportTemplateList(new ArrayList<ImportTemplate>());
        }
        workingCopyScript.getImportTemplateList().clear();
        for (ImportTemplate importTemplate : importTemplateDualList.getTarget()) {
            workingCopyScript.getImportTemplateList().add(importTemplate);
        }
    }

    public void addImportTemplatesToPickList() {
        importTemplateDualList.getTarget().clear();
        importTemplateDualList.getSource().clear();
        if (workingCopyScript.getImportTemplateList() != null) {
            for (ImportTemplate importTemplate : workingCopyScript.getImportTemplateList()) {
                importTemplateDualList.getTarget().add(importTemplate);
            }
        }
        for (ImportTemplate importTemplate : nonDefaultImportTemplateList) {
            if (importTemplateDualList.getTarget().indexOf(importTemplate) < 0) {
                importTemplateDualList.getSource().add(importTemplate);
            }
        }
    }

    public void changeSelectedScript() {
        workingCopyScript = selectedScript;
        addImportTemplatesToPickList();
    }

    public void createNewScript() {
        workingCopyScript = new Rule();
        addImportTemplatesToPickList();
    }

}
