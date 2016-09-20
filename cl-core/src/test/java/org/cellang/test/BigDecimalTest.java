package org.cellang.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import junit.framework.TestCase;

public class BigDecimalTest extends TestCase{

	public void test(){
		BigDecimal bd = new BigDecimal("0.0012345678");
		System.out.println(String.valueOf(bd.unscaledValue()).length() - bd.scale());
		
		bd = new BigDecimal("0.1").pow(2);//
		System.out.println(bd);
		
//		bd = bd.setScale(2,RoundingMode.CEILING);
//		
//		System.out.println(bd.doubleValue());
//		System.out.println(bd.scale());
//		System.out.println(bd.unscaledValue());
		
	}
}
