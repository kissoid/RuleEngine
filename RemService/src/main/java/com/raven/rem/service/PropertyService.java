/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.service;

import com.raven.rem.dao.PropertyCodeDao;
import com.raven.rem.dao.PropertyDao;
import com.raven.rem.dao.PropertyValueDao;
import com.raven.rem.entity.Property;
import com.raven.rem.entity.PropertyCode;
import com.raven.rem.entity.PropertyValue;
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
@Stateless(name = "propertyService", mappedName = "propertyService")
@LocalBean
public class PropertyService {

    @EJB
    private PropertyDao propertyDao;
    @EJB
	private PropertyCodeDao propertyCodeDao;
	@EJB
	private PropertyValueDao propertyValueDao;

    public List<Property> retrieveProperties() throws Exception {
        return propertyDao.findAll();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveProperty(Property property) {
        if (property.getPropertyId() == null) {
			propertyDao.create(property);
        } else {
            propertyDao.update(property);
		}
    }

    public List<PropertyCode> retrievePropertyCodesByDescription(String description) throws Exception {
        String jpql = "select pc from IremPropertyCode pc where pc.description like :description ";
        Map params = new HashMap();
        params.put("description", "%" + description + "%");
        return propertyCodeDao.findListByQuery(jpql, params);
    }

    public List<PropertyCode> retrievePropertyCodesByModule() throws Exception {
        /*Burası modül parametresine göre gelecek şekilde düzenlenecek*/
        return propertyCodeDao.findAll();
	}

	public List<PropertyValue> retrievePropertyValuesByPropertyId(Short propertyId) throws Exception {
		String jpql = "select pv from IremPropertyValue pv where pv.iremProperty.iremPropertyId=:propertyId";
		Map<String, Short> params = new HashMap<String, Short>();
		params.put("iremPropertyId", propertyId);
		return propertyValueDao.findListByQuery(jpql, params);
	}

}
