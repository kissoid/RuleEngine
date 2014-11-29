/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.script;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mehmet Adem Sengul
 */
public class ScriptResult {

    private Short scriptStatus;
    private String scriptMessage;
    private Map scriptValueMap;

    public ScriptResult() {
        
    }

    public ScriptResult(Short scriptStatus, String scriptMessage) {
        this.scriptStatus = scriptStatus;
        this.scriptMessage = scriptMessage;
    }

    public ScriptResult(Short scriptStatus, String scriptMessage, Map scriptResultValue) {
        this.scriptStatus = scriptStatus;
        this.scriptMessage = scriptMessage;

    }

    public Short getScriptStatus() {
        return scriptStatus;
    }

    public void setScriptStatus(Short scriptStatus) {
        this.scriptStatus = scriptStatus;
    }

    public String getScriptMessage() {
        return scriptMessage;
    }

    public void setScriptMessage(String scriptMessage) {
        this.scriptMessage = scriptMessage;
    }

	public Map getScriptValueMap() {
		if (scriptValueMap == null) {
			scriptValueMap = new HashMap();
		}
        return scriptValueMap;
    }

    public void setScriptValueMap(Map scriptValueMap) {
        this.scriptValueMap = scriptValueMap;
    }

    public void addToValueMap(Object key, Object value) {
        if (scriptValueMap == null) {
            scriptValueMap = new HashMap();
        }
        scriptValueMap.put(key, value);
    }

    public Object getFromValueMap(Object key) {
        if (scriptValueMap == null) {
            return null;
        }
        return scriptValueMap.get(key);
    }

}
