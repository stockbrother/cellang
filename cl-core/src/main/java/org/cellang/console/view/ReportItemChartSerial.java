package org.cellang.console.view;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.chart.ChartSingleSerial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportItemChartSerial extends ChartSingleSerial<Long> {
	private static final Logger LOG = LoggerFactory.getLogger(ReportItemChartSerial.class);

	int pageSize;
	List<Long> dateL;
	List<BigDecimal> valueL;
	Map<Long, BigDecimal> valueMap;

	public ReportItemChartSerial(int pageSize) {
		super("default");
		this.pageSize = pageSize;
	}

	public void setSerial(List<Long> dateL, List<BigDecimal> valueL) {
		this.dateL = dateL;
		this.valueL = valueL;
		this.valueMap = new HashMap<>();
		for (int i = 0; i < dateL.size(); i++) {
			Long key = dateL.get(i);
			this.valueMap.put(key, valueL.get(i));
		}
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

}
