package org.cellang.console.view;

import org.cellang.console.chart.ChartModel;
import org.cellang.console.chart.LineChart;
import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.Descriable;

public class ChartView<T> extends AbstractView {
	LineChart<T> chart;
	ChartModel<T> model;
	AbstractChartDataProvider<T> data;

	public ChartView(String title, AbstractChartDataProvider<T> cd) {
		super(title);
		cd.setView(this);//
		model = cd.getModel();
		this.data = cd;

		this.chart = new LineChart<T>(model);
		this.setViewportView(this.chart);
		this.data.nextPage();
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (cls.equals(DataPageQuerable.class)) {
			return (T) this.data;
		} else if (cls.equals(Descriable.class)) {
			return (T) this.data;
		}
		return null;
	}

}
