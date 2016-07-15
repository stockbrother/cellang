package org.cellang.console.view;

import org.cellang.console.chart.ChartModel;
import org.cellang.console.control.DataPageQuerable;

public abstract class AbstractChartDataProvider<T> implements DataPageQuerable {

	protected int pageSize;

	protected int pageNumber = -1;
	ChartView view;
	ChartModel<T> model;

	public AbstractChartDataProvider(ChartModel<T> model, int pageSize) {
		this.pageSize = pageSize;
		this.model = model;
	}

	public void setView(ChartView view) {
		this.view = view;
	}

	public ChartModel<T> getModel() {
		return model;
	}

	@Override
	public void prePage() {
		if (this.pageNumber == 0) {
			return;
		}
		this.pageNumber--;
		this.query();
	}

	protected abstract void query();

	@Override
	public void nextPage() {
		this.pageNumber++;
		this.query();
	}
}
