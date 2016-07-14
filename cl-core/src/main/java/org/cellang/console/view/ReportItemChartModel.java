package org.cellang.console.view;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cellang.console.chart.ChartModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportItemChartModel extends ChartModel {
	private static final Logger LOG = LoggerFactory.getLogger(ReportItemChartModel.class);

	int pageSize;
	List<Long> dateL;
	List<BigDecimal> valueL;

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd");

	public ReportItemChartModel(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setSerial(List<Long> dateL, List<BigDecimal> valueL) {
		this.dateL = dateL;
		this.valueL = valueL;
		super.invalidCache();
	}

	@Override
	public int getCount() {
		return this.pageSize;
	}

	@Override
	public String getXValue(int idx) {
		if (this.dateL == null) {
			return null;
		}
		if (idx >= this.dateL.size()) {
			return null;
		}
		Long date = dateL.get(idx);
		return FORMAT.format(new Date(date));
	}

	@Override
	public BigDecimal getYValue(int idx) {
		if (this.valueL == null) {
			return null;
		}
		if (idx >= this.valueL.size()) {
			return null;
		}
		BigDecimal rt = valueL.get(idx);
		return rt;

	}

}
