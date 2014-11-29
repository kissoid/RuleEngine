/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.common.util;

/**
 *
 * @author Mehmet Adem Sengul
 */
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionActionListener;
import javax.servlet.http.HttpSession;

public class FacesUtil {

    public static void createFacesMessage(String message, String detail, FacesMessage.Severity severity) {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSummary(message);
        if (detail != null) {
            facesMessage.setDetail(detail);
        }
        facesMessage.setSeverity(severity);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public static void clearFacesMessages() {
        FacesContext.getCurrentInstance().getMessageList().clear();
    }

    public static UIComponent findComponent(String id) {
        UIComponent ret = null;
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            UIComponent root = context.getViewRoot();
            ret = FacesUtil.findComponent(root, id);
        }
        return ret;
    }
    
    private static UIComponent findComponent(UIComponent base, String id) {
        if (id.equals(base.getId())) {
            return base;
        }
        UIComponent kid = null;
        UIComponent result = null;
        Iterator<UIComponent> kids = base.getFacetsAndChildren();
        while (kids.hasNext() && (result == null)) {
            kid = (UIComponent) kids.next();
            if (id.equals(kid.getId())) {
                result = kid;
                break;
            }
            result = FacesUtil.findComponent(kid, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }
    
    public static List<UIComponent> findComponentInstances(Class clazz) {
        List<UIComponent> uiComponentList = null;
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            UIComponent root = context.getViewRoot();
            uiComponentList = FacesUtil.findComponentInstances(root, clazz);
        }
        return uiComponentList;
    }

    private static List<UIComponent> findComponentInstances(UIComponent base, Class clazz) {
        List<UIComponent> uiComponentList = new ArrayList<UIComponent>();
        UIComponent kid = null;
        Iterator<UIComponent> kids = base.getFacetsAndChildren();
        while (kids.hasNext()) {
            kid = (UIComponent) kids.next();
            if (kid.getClass() == clazz) {
                uiComponentList.add(kid);
            }
            List<UIComponent> tempUiComponentList = FacesUtil.findComponentInstances(kid, clazz);
            if (tempUiComponentList != null && !tempUiComponentList.isEmpty()) {
                uiComponentList.addAll(tempUiComponentList);
            }
        }
        return uiComponentList;
    }
    
    public static MethodExpression createActionExpression(String actionExpression) {
        Class<?>[] paramTypes = new Class<?>[0];
        MethodExpression methodExpression = getExpressionFactory().createMethodExpression(getELContext(), actionExpression, null, paramTypes);
        return methodExpression;
    }

    public static MethodExpressionActionListener createActionListenerExpression(String actionListenerExpression) {
        FacesContext context = FacesContext.getCurrentInstance();
        MethodExpressionActionListener methodExpressionActionListener = new MethodExpressionActionListener(getExpressionFactory().createMethodExpression(context.getELContext(), actionListenerExpression, null, new Class[]{ActionEvent.class}));
        return methodExpressionActionListener;
    }

    public static ValueExpression createValueExpression(String value, Class clazz) {
        ValueExpression valueExpression = getExpressionFactory().createValueExpression(getELContext(), value, clazz);
        return valueExpression;
    }

    public static ELContext getELContext() {
        return FacesContext.getCurrentInstance().getELContext();
    }

    public static ExpressionFactory getExpressionFactory() {
        return getApplication().getExpressionFactory();
    }

    public static Application getApplication() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    public static Map<String, Object> getRequestMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
    }

    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }

    public static HttpSession getSession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return session;
    }
}
