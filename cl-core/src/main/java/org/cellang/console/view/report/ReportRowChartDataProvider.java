package org.cellang.console.view.report;

import java.math.BigDecimal;
import java.util.Date;

import org.cellang.collector.EnvUtil;
import org.cellang.console.chart.ChartModel;
import org.cellang.console.chart.ChartSerial;
import org.cellang.console.view.chart.AbstractChartDataProvider;

public class ReportRowChartDataProvider extends AbstractChartDataProvider<Date> {
	public static class ReportRowChartSerial extends ChartSerial<Date> {
		ReportRow row;

		public ReportRowChartSerial(ReportRow rowObj) {
			super(rowObj.getKey());
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

	public void setReportRow(ReportRow rowObj) {
		ChartSerial<Date> cs = new ReportRowChartSerial(rowObj);
		this.model.addSerail(cs);
		this.view.updateUI();
	}

}
