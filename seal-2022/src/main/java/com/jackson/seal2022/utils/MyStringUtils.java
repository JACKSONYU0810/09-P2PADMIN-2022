package com.jackson.seal2022.utils;

import java.math.BigDecimal;

/**
 * 一些字符串的处理
 * 
 * @author yanglijun
 *
 */
public class MyStringUtils {
	
	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 将数字转化为大写  
	 * 
	 * @param num
	 * @return
	 */
    public static String numToUpper(int num) {  
        String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};  
        char[] str = String.valueOf(num).toCharArray();  
        String rstr = "";  
        for (int i = 0; i < str.length; i++) {  
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }  
        return rstr;  
    }
}