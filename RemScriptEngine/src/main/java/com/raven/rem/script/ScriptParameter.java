/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.script;

/**
 *
 * @author Mehmet Adem Sengul
 */
public class ScriptParameter {

    private String code;
    private String description;
    private Object value;

    public ScriptParameter() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getRowKey() {
        return this.hashCode();
    }

}
