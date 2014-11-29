/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.ws.input;

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
public class ScriptExecuterInput implements Serializable {

    @XmlElement
    private String key;
    @XmlElement
    private Object value;
    @XmlElement
    private List<ScriptExecuterInput> valueList;

    public ScriptExecuterInput() {

    }

    public ScriptExecuterInput(String code, Object value) {
        this.key = code;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<ScriptExecuterInput> getValueList() {
        if (valueList == null) {
            valueList = new ArrayList<ScriptExecuterInput>();
        }
        return valueList;
    }


}
