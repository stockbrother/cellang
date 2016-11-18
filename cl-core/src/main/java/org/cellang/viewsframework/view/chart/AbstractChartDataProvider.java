package org.cellang.viewsframework.view.chart;

import java.util.Map;

import org.cellang.viewsframework.chart.ChartModel;
import org.cellang.viewsframework.control.DataPageQuerable;
import org.cellang.viewsframework.control.Descriable;
import org.cellang.viewsframework.control.Refreshable;

/**
 * TODO remove pageXXX.
 * 
 * @author wu
 *
 * @param <T>
 */
public abstract class AbstractChartDataProvider<T> implements DataPageQuerable, Descriable, Refreshable {

	protected int pageSize;

	protected int pageNumber = -1;
	protected ChartModel<T> model;

	public AbstractChartDataProvider(int pageSize) {
		this(new ChartModel<T>(), pageSize);
	}

	public AbstractChartDataProvider(ChartModel<T> model, int pageSize) {
		this.pageSize = pageSize;
		this.model = model;
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
