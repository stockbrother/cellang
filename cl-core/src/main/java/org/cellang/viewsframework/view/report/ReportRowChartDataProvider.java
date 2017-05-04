package org.cellang.viewsframework.view.report;

import java.math.BigDecimal;
import java.util.Date;

import org.cellang.collector.EnvUtil;
import org.cellang.viewsframework.chart.AbstractChartDataProvider;
import org.cellang.viewsframework.chart.ChartModel;
import org.cellang.viewsframework.chart.ChartSerial;

public class ReportRowChartDataProvider extends AbstractChartDataProvider<Date> {
	public static class ReportRowChartSerial extends ChartSerial<Date> {
		ReportRow row;

		public ReportRowChartSerial(String key,ReportRow rowObj) {
			super(key);
			this.row = rowObj;
		}

		@Override
		public BigDecimal getYValue(Date xValue) {
			int year = EnvUtil.getYear(xValue);
			int idx = 2015 - year;
			return this.row.getValue(idx);
		}

		@Override
		public int getWindowSize() {
			return this.row.getSize();
		}

		@Override
		public void moveWindowTo(Date startXValue) {
			throw new RuntimeException("not supported.");
		}

		@Override
		public Date getXValue(int idx) {
			int year = 2015 - idx;
			return EnvUtil.newDateOfYearLastDay(year);
		}
	}

	public ReportRowChartDataProvider() {
		super(new ChartModel<Date>(), 10);
	}

	@Override
	protected void query() {

	}

	public boolean addReportRow(ReportRow rowObj) {
		String key = rowObj.getKey();
		if(this.model.getSerial(key) == null){
			ChartSerial<Date> cs = new ReportRowChartSerial(key,rowObj);
			this.model.addSerail(cs);			
			return true;
		}else{
			return false;
		}
	}

}
