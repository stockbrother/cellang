package org.cellang.console.model;

import java.util.Map;

import org.cellang.console.chart.ChartModel;
import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.Descriable;
import org.cellang.console.view.ChartView;

public abstract class AbstractChartDataProvider<T> implements DataPageQuerable, Descriable {

	protected int pageSize;

	protected int pageNumber = -1;
	ChartView<T> view;
	ChartModel<T> model;

	public AbstractChartDataProvider(ChartModel<T> model, int pageSize) {
		this.pageSize = pageSize;
		this.model = model;
	}

	public void setView(ChartView<T> view) {
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

	@Override
	public void getDescription(Map<String, Object> desMap) {
		desMap.put("PageSize", this.pageSize);
	}

	@Override
	public void refresh() {
		this.query();
	}

}
