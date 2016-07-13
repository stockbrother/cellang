package org.cellang.console.view;

import java.lang.reflect.Method;
import java.util.List;

import org.cellang.console.chart.LineChart;
import org.cellang.console.control.DataPageQuerable;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityService;

public class ChartView extends AbstractView implements DataPageQuerable {
	LineChart chart;

	protected EntityService entityService;
	protected int pageSize;

	protected int pageNumber = -1;
	EntityChartModel model;
	EntityConfig cfg;

	public ChartView(String title, EntityConfig cfg, Method xGetMethod, Method yGetMethod, int pageSize,
			EntityService es) {
		super(title);
		this.cfg = cfg;
		this.entityService = es;
		this.pageSize = pageSize;
		model = new EntityChartModel(cfg, xGetMethod, yGetMethod, pageSize);
		this.chart = new LineChart(model);
		this.setViewportView(this.chart);
		this.nextPage();
	}

	@Override
	public void prePage() {
		if (this.pageNumber == 0) {
			return;
		}
		this.pageNumber--;
		this.query();
	}

	private void query() {
		int offset = this.pageNumber * this.pageSize;
		List<? extends EntityObject> el = this.entityService.query(cfg.getEntityClass()).offset(offset)
				.limit(this.pageSize).executeQuery();
		model.setEntityObjectList(el);

		this.updateUI();
	}

	@Override
	public void nextPage() {
		List<? extends EntityObject> list = model.getEntityObjectList();
		if (list != null && list.size() < this.pageSize) {
			return;
		}
		this.pageNumber++;
		this.query();

	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (cls.equals(DataPageQuerable.class)) {
			return (T) this;
		}
		return null;
	}
}
