package org.cellang.viewsframework.customized;

import java.math.BigDecimal;

public interface CustomizedReportItemDefine {
	
	public String getKey();

	public BigDecimal getValue(String corpId, int year);
	
	public boolean install(Object obj);
}
