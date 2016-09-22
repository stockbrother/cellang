package org.cellang.console.ops;

import java.lang.reflect.Method;

import org.cellang.console.view.chart.ChartView;
import org.cellang.console.view.chart.SimpleChartDataProvider;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;

public class SimpleChartView extends ChartView {

	public SimpleChartView(String title, int pageSize, EntitySessionFactory es, EntityConfig cfg, Method xGetMethod,
			Method yGetMethod) {
		super(title, null, new SimpleChartDataProvider(pageSize, es, cfg, xGetMethod, yGetMethod));
	}

}
