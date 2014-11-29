/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.script;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.util.HashMap;
import java.util.Map;





/**
 *
 * @author Mehmet Adem Sengul
 */
public class ScriptUtil {

    public static Map<String, Script> scriptCache = new HashMap<String, Script>();
    
    private static Binding cerateParameters() {
        Binding binding = new Binding();
        /*for (Map.Entry<String, String> entry : componentMap.entrySet()) {
            String componentId = entry.getValue();
            UIComponent uIComponent = FacesUtil.findComponent(componentId);
            binding.setVariable(entry.getKey(), uIComponent);
        }*/
        return binding;
    }

    public static void evaluateScript(String imports, String eventScript) throws Exception {
        GroovyShell shell = new GroovyShell(cerateParameters());
        StringBuilder script = new StringBuilder();
        script.append(getStandartImports()).append("\n");
        script.append(imports == null ? "" : imports.replace("null", "")).append("\n");
        shell.evaluate(script.toString());
    }
    
    public static Script createJavaClassFromScript(String scriptText){
        GroovyShell shell = new GroovyShell(cerateParameters());
        return shell.parse(scriptText);
    }

    private static String getStandartImports() {
        StringBuilder imports = new StringBuilder();
        imports.append("import javax.naming.Context;\n");
        imports.append("import javax.naming.InitialContext;\n");
        imports.append("import java.util.List;\n");
        imports.append("import java.util.ArrayList;\n");
        imports.append("import java.util.Map;\n");
        imports.append("import java.util.HashMap;\n");
        imports.append("import com.ergo.insyst.ddm.util.UIDHelper;\n");
        imports.append("import com.ergo.insyst.ddm.util.UIUtil;\n");
        imports.append("import com.ergo.insyst.common.util.FacesUtil;\n");
        imports.append("import javax.faces.application.FacesMessage;\n");
        imports.append("import org.primefaces.context.RequestContext;\n");
        imports.append("import com.ergo.insyst.ddm.util.ExpressionUtil;\n");
        imports.append("import com.ergo.insyst.common.component.Lookup;\n");
        imports.append("import org.primefaces.component.inputtext.InputText;\n");
        imports.append("import org.primefaces.component.panelgrid.PanelGrid;\n");
        imports.append("import org.primefaces.component.tabview.Tab;\n");
        imports.append("import org.primefaces.component.tabview.TabView;\n");
        imports.append("import org.primefaces.component.column.Column;\n");
        imports.append("import org.primefaces.component.row.Row;\n");
        imports.append("import org.primefaces.component.panel.Panel;\n");
        imports.append("import org.primefaces.component.panelgrid.PanelGrid;\n");
        imports.append("import org.primefaces.component.calendar.Calendar;\n");
        imports.append("import org.primefaces.component.commandbutton.CommandButton;\n");
        imports.append("import org.primefaces.component.selectonebutton.SelectOneButton;\n");
        imports.append("import org.primefaces.component.selectoneradio.SelectOneRadio;\n");
        imports.append("import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;\n");
        return imports.toString();
    }
}
