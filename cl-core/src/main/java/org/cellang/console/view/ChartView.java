package org.cellang.console.view;

import org.cellang.console.chart.ChartModel;
import org.cellang.console.chart.LineChart;
import org.cellang.console.control.DataPageQuerable;

public class ChartView extends AbstractView {
	LineChart chart;
	ChartModel model;
	AbstractChartDataProvider data;

	public ChartView(String title, AbstractChartDataProvider cd) {
		super(title);
		cd.setView(this);//
		model = cd.getModel();
		this.data = cd;

		this.chart = new LineChart(model);
		this.setViewportView(this.chart);
		this.data.nextPage();
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (cls.equals(DataPageQuerable.class)) {
			return (T) this.data;
		}
		return null;
	}
}
