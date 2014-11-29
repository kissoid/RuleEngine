/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.ws.output;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mehmet Adem Sengul
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScriptExecuterOutput implements Serializable {

    @XmlElement
    private Short scriptStatus;
    @XmlElement
    private String scriptMessage;
    @XmlElement
    private List<OutputItem> scriptValueList;

    public ScriptExecuterOutput() {

    }

    public ScriptExecuterOutput(Short scriptStatus, String scriptMessage) {
        this.scriptStatus = scriptStatus;
        this.scriptMessage = scriptMessage;
    }

    public ScriptExecuterOutput(Short scriptStatus, String scriptMessage, List<OutputItem> scriptValueList) {
        this.scriptStatus = scriptStatus;
        this.scriptMessage = scriptMessage;
        this.scriptValueList = scriptValueList;
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

    public List<OutputItem> getScriptValueList() {
        if (scriptValueList == null) {
            scriptValueList = new ArrayList<OutputItem>();
        }
        return scriptValueList;
    }

    public void addToValueList(Object key, Object value) {
        if (scriptValueList == null) {
            scriptValueList = new ArrayList<OutputItem>();
        }
        OutputItem outputItem = findInList(key);
        if (outputItem != null) {
            outputItem.setValue(value);
        } else {
            scriptValueList.add(new OutputItem(key, value));
        }
    }

    public Object getFromValueList(Object key) {
        if (scriptValueList == null) {
            return null;
        }
        return findInList(key);
    }

    private OutputItem findInList(Object key) {
        for (OutputItem outputItem : scriptValueList) {
            if (outputItem.getKey().equals(key)) {
                return outputItem;
            }
        }
        return null;
    }

}
