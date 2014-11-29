/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.service;

import com.raven.rem.dao.FormulaDao;
import com.raven.rem.dao.FormulaPropertyDao;
import com.raven.rem.dao.FormulaValueDao;
import com.raven.rem.dao.FormulaValueSeqDao;
import com.raven.rem.entity.Formula;
import com.raven.rem.entity.FormulaProperty;
import com.raven.rem.entity.FormulaValue;
import com.raven.rem.entity.FormulaValueSeq;
import com.raven.rem.entity.PropertyValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Stateless(name = "formulaService", mappedName = "formulaService")
@LocalBean
public class FormulaService {

	@EJB
	private FormulaPropertyDao formulaPropertyDao;
	@EJB
	private FormulaDao formulaDao;
	@EJB
	private FormulaValueDao formulaValueDao;
	@EJB
	private FormulaValueSeqDao formulaValueSeqDao;

	public List<FormulaProperty> retrieveFormulaProperties(Short iremFormulaId) throws Exception {
		String jpql = "select i from IremFormulaProperty i where i.iremFormula.iremFormulaId = :iremFormulaId ";
		Map params = new HashMap();
		params.put("iremFormulaId", iremFormulaId);
		return formulaPropertyDao.findListByQuery(jpql, params);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveFormulaProperty(FormulaProperty iremFormulaProperty) {
		formulaPropertyDao.create(iremFormulaProperty);
	}

	public List<Formula> retrieveFormulas() throws Exception {
		return formulaDao.findAll();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Formula saveFormula(Formula iremFormula) throws Exception {
		if (iremFormula.getFormulaId() == null) {
			return formulaDao.createAndReturn(iremFormula);
		} else {
			formulaDao.update(iremFormula);
		}
		return iremFormula;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveFormulaValueSeq(FormulaValueSeq formulaValueSeq) throws Exception {
		if (formulaValueSeq.getFormulaValueId() == null) {
			formulaValueSeqDao.create(formulaValueSeq);
		} else {
			formulaValueSeqDao.update(formulaValueSeq);
		}
	}

	public Integer createFormulaValues(Formula formula) throws Exception {
		String sql = "{call SP_IRM_FORMULA_VALUE_GENERATOR(?)}";
		Map<Integer, Short> params = new HashMap<Integer, Short>();
		params.put(1, formula.getFormulaId());
		return formulaDao.executeNativeQuery(sql, params);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void createCartesian(Formula formula, List<FormulaValue> formulaValueList, int formulaPropertyIndex) throws Exception {
		if (formulaValueList == null) {
			formulaValueList = new ArrayList<FormulaValue>();
		}
		FormulaProperty formulaProperty = formula.getFormulaPropertyList().get(formulaPropertyIndex);

		if (formulaProperty.getProperty().getPropertyValueList().isEmpty()) {
			throw new Exception(formulaProperty.getProperty().getPropertyId() + " : " + formulaProperty.getProperty().getDescription() + " propertisine hiç değer atanmamış");
		} else {
			for (PropertyValue propertyValue : formulaProperty.getProperty().getPropertyValueList()) {

				FormulaValue formulaValue = new FormulaValue();
				formulaValue.setFormulaProperty(formulaProperty);
				formulaValue.setPropertyValue(propertyValue);

				List<FormulaValue> tempIremFormulaValueList = new ArrayList<FormulaValue>(formulaValueList);
				tempIremFormulaValueList.add(formulaValue);

				if (formulaPropertyIndex + 1 < formula.getFormulaPropertyList().size()) {
					createCartesian(formula, tempIremFormulaValueList, formulaPropertyIndex + 1);
				} else {
					FormulaValueSeq formulaValueSeq = new FormulaValueSeq();
					formulaValueSeq.setFormula(formula);

					if (formulaValueSeq.getFormulaValueList() == null) {
						formulaValueSeq.setFormulaValueList(new ArrayList<FormulaValue>());
					}
					
					for (FormulaValue tempFormulaValue : tempIremFormulaValueList) {
						FormulaValue cloneFormulaValue = new FormulaValue();
						cloneFormulaValue.setFormulaProperty(tempFormulaValue.getFormulaProperty());
						cloneFormulaValue.setFormulaValueSeq(formulaValueSeq);
						cloneFormulaValue.setPropertyValue(propertyValue);
						formulaValueSeq.getFormulaValueList().add(cloneFormulaValue);
					}

					if (!formulaValueSeq.getFormulaValueList().isEmpty()) {
						saveFormulaValueSeq(formulaValueSeq);
					}
				}
			}
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Formula saveFormulaAndCreateFormulaValues(Formula formula) throws Exception {
		formula = saveFormula(formula);
		createCartesian(formula, null, 0);
		return formula;
	}

	public List<FormulaValue> retrieveFormulaValue(List<Integer> propertyValueIdList) throws Exception {
		String jpql = "select fv from IremFormulaValue fv where pv.iremPropertyValueId in ";
		jpql += " (select distinct c.iremFormulaValueId from IremFormula a inner join ";
		jpql += " IremFormulaProperty b inner join IremFormulaValue c where ";
		jpql += " a.iremFormulaId= b.iremFormulaPropertyPK.iremFormulaId ";
		jpql += " and c.iremFormulaId=a.iremFormulaId ";
		jpql += " and b.iremPropertyId=c.iremPropertyId ";
		jpql += " and c.iremPropertyValueId in :propertyValueIdList ";
		jpql += " and (SELECT count(f) FROM IremFormulaProperty f  WHERE f.iremformulaPropertyPK.iremFormulaId = a.iremFormulaId) = ";
		jpql += " (SELECT count(g) FROM IremFormulaValue g WHERE g.iremformulaValueId = c.iremformulaValueId and g.irempropertyValueId in :propertyValueIdList )";
		Map<String, List<Integer>> params = new HashMap<String, List<Integer>>();
		params.put("propertyValueIdList", propertyValueIdList);
		return formulaValueDao.findListByQuery(jpql, params);
	}

}
