/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.service;

import groovy.lang.Script;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ScriptCacheService {

    private ConcurrentHashMap<Integer, Script> scriptCache = null;

    @PostConstruct
    public void initialize() {
        this.scriptCache = new ConcurrentHashMap();
    }

    public void putCache(Integer scriptId, Script script) {
        scriptCache.put(scriptId, script);
    }

    public Script getCache(Integer scriptId) {
        return scriptCache.get(scriptId);
    }

	public Script removeCache(Integer scriptId) {
		return scriptCache.remove(scriptId);
	}

}
