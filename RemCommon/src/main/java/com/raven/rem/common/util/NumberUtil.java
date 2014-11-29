/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.raven.rem.common.util;

import java.text.DecimalFormat;

/**
 *
 * @author Mehmet Adem Sengul
 */
public class NumberUtil {
    
    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    } 
    
    public static String amountFormat(double amount) {
		if (StringUtil.isEmpty(amount))
			return "";

		DecimalFormat money = new DecimalFormat("#,###.##");
		return  money.format(amount);
    }
    
}
