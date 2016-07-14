package org.cellang.console.view;

import java.lang.reflect.Method;
import java.util.List;

import org.cellang.console.chart.ChartModel;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySessionFactory;

public class SimpleChartDataProvider extends AbstractChartDataProvider {
	EntitySessionFactory esf;
	EntityConfig ec;
	EntityChartModel model;
	

	public SimpleChartDataProvider(int pageSize, EntitySessionFactory esf, EntityConfig ec, Method xGetMethod,
			Method yGetMethod) {
		super(pageSize);

		this.model = new EntityChartModel(ec, xGetMethod, yGetMethod, pageSize);

		this.esf = esf;
		this.ec = ec;
	}

	protected void query() {
		int offset = this.pageNumber * this.pageSize;
		List<? extends EntityObject> el = new EntityQuery<>(ec).offset(offset).limit(this.pageSize).execute(this.esf);

		((EntityChartModel) model).setEntityObjectList(el);

		this.view.updateUI();
	}

	@Override
	public void nextPage() {
		List<? extends EntityObject> list = ((EntityChartModel) model).getEntityObjectList();
		if (list != null && list.size() < this.pageSize) {
			return;
		}
		super.nextPage();
	}

	@Override
	public ChartModel getModel() {
		return this.model;
	}

}
