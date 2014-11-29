/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.ws.output;

import java.io.Serializable;

/**
 *
 * @author Mehmet Adem Sengul
 */
public class OutputItem implements Serializable {

    private Object key;
    private Object value;

    public OutputItem() {

    }

    public OutputItem(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}