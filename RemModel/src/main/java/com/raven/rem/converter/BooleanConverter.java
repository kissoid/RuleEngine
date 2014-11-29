/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Mehmet Adem Sengul
 */
@Converter
public class BooleanConverter implements AttributeConverter<Boolean, Short> {

    @Override
    public Short convertToDatabaseColumn(Boolean x) {
        if (x == null) {
            return 0;
        }
        if (x.equals(Boolean.TRUE)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Boolean convertToEntityAttribute(Short y) {
        if (y == null) {
            return false;
        }
        if (y.equals(1)) {
            return true;
        } else if (y.equals(0)) {
            return false;
        } else {
            return false;
        }
    }

}
