package org.cellang.console.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.lang.Tuple2;
import org.cellang.console.view.report.ReportTemplateRowChartDataProvider;

import junit.framework.TestCase;

public class ReportTemplateRowChartTest extends TestCase {

	public void testChartSerial() {
		List<Tuple2<String, BigDecimal>> list = new ArrayList<>();
		list.add(new Tuple2<String,BigDecimal>("11",new BigDecimal(".11")));
		list.add(new Tuple2<String,BigDecimal>("12",new BigDecimal(".12")));
		list.add(new Tuple2<String,BigDecimal>("21",new BigDecimal(".21")));
		list.add(new Tuple2<String,BigDecimal>("22",new BigDecimal(".22")));
		list.add(new Tuple2<String,BigDecimal>("31",new BigDecimal(".31")));
		list.add(new Tuple2<String,BigDecimal>("32",new BigDecimal(".32")));
		list.add(new Tuple2<String,BigDecimal>("41",new BigDecimal(".41")));
		list.add(new Tuple2<String,BigDecimal>("42",new BigDecimal(".42")));
		list.add(new Tuple2<String,BigDecimal>("51",new BigDecimal(".51")));
		list.add(new Tuple2<String,BigDecimal>("52",new BigDecimal(".52")));
		
		ReportTemplateRowChartDataProvider.ReportTemplateRowChartSerial cs = new ReportTemplateRowChartDataProvider.ReportTemplateRowChartSerial(
				"key", list);
		
		int size = cs.getWindowSize();

	}
}
