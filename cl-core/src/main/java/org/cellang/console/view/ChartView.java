package org.cellang.console.view;

import org.cellang.console.chart.LineChart;

public class ChartView extends AbstractView {
	LineChart chart;

	public ChartView(String title) {
		super(title);
		this.chart = new LineChart(new CompoundGrowModel(10000, 0.1, 20));
		this.setViewportView(this.chart);
	}

}
