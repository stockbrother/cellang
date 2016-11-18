package org.cellang.viewsframework.ops;

import java.lang.reflect.Method;

import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.viewsframework.view.chart.ChartView;
import org.cellang.viewsframework.view.chart.SimpleChartDataProvider;

public class SimpleChartView extends ChartView {

	public SimpleChartView(String title, int pageSize, EntitySessionFactory es, EntityConfig cfg, Method xGetMethod,
			Method yGetMethod) {
		super(title, null, new SimpleChartDataProvider(pageSize, es, cfg, xGetMethod, yGetMethod));
	}

}
