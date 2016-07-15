package org.cellang.console.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.chart.ChartSingleSerial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportItemChartSerial extends ChartSingleSerial<ReportDate> {
	private static final Logger LOG = LoggerFactory.getLogger(ReportItemChartSerial.class);

	ReportDate offset;

	Map<ReportDate, BigDecimal> valueMap = new HashMap<>();
	protected int windowSize;
	boolean desc = true;

	public ReportItemChartSerial(String name, ReportDate offset, int pageSize) {
		super(name);
		this.offset = offset;
		this.windowSize = pageSize;
	}

	public void addPoint(ReportDate xValue, BigDecimal yValue) {

		this.valueMap.put(xValue, yValue);
	}

	@Override
	public int getWindowSize() {
		return this.windowSize;
	}

	@Override
	public ReportDate getXValue(int idx) {

		return this.offset.add(desc ? -idx : idx);
	}

	@Override
	public BigDecimal getYValue(ReportDate date) {
		if (this.valueMap == null) {
			return null;
		}
		BigDecimal rt = valueMap.get(date);
		return rt;

	}

	@Override
	public void moveWindowTo(ReportDate offset) {
		this.offset = offset;
	}
}
