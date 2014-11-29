/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.common.util;

import java.lang.management.ManagementFactory;

import javax.faces.context.FacesContext;

/**
 *
 * @author Mehmet Adem Sengul
 */
public class SystemUtil {

    public static boolean isDebugEnabled() {
        return (ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp"));
    }
    
    public static String getSystemParameter(String paramName) {
        return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(paramName).toString();
    }

}
