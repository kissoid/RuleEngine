/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.service;

import com.raven.rem.dao.ScriptDao;
import com.raven.rem.entity.Rule;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author Mehmet Adem Sengul
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless(name = "scriptService", mappedName = "scriptService")
@LocalBean
public class ScriptService {

    @EJB
    private ScriptDao iremScriptDao;

    public List<Rule> retrieveScripts() throws Exception {
        return iremScriptDao.findAll();
    }

    public Rule retrieveScript(Integer scriptId) {
        return iremScriptDao.find(scriptId);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Rule saveScript(Rule script) throws Exception {
        if (script.getRuleId() == null) {
            return iremScriptDao.createAndReturn(script);
        } else {
            iremScriptDao.update(script);
        }
        return script;
    }

}
