package org.cellang.console.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.chart.ChartSingleSerial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportItemChartSerial extends ChartSingleSerial<Long> {
	private static final Logger LOG = LoggerFactory.getLogger(ReportItemChartSerial.class);

	int pageSize;
	List<Long> dateL = new ArrayList<Long>();
	List<BigDecimal> valueL = new ArrayList<>();
	Map<Long, BigDecimal> valueMap = new HashMap<>();

	public ReportItemChartSerial(String name, int pageSize) {
		super(name);
		this.pageSize = pageSize;
	}

	public void addPoint(Long xValue, BigDecimal yValue) {
		this.dateL.add(xValue);
		this.valueL.add(yValue);
		this.valueMap.put(xValue, yValue);

		super.modified();
	}

	@Override
	public int getXCount() {
		if (this.dateL == null) {
			return 0;
		}
		return this.dateL.size();
	}

	@Override
	public Long getXValue(int idx) {
		if (this.dateL == null) {
			return null;
		}
		if (idx >= this.dateL.size()) {
			return null;
		}
		Long rt = dateL.get(idx);
		return rt;
	}

	@Override
	public BigDecimal getYValue(Long date) {
		if (this.valueMap == null) {
			return null;
		}
		BigDecimal rt = valueMap.get(date);
		return rt;

	}

	@Override
	public void clearPoints() {
		this.dateL.clear();
		this.valueL.clear();
		this.valueMap.clear();
		super.modified();
	}
}
