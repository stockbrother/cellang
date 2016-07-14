package org.cellang.console.ops;

import java.lang.reflect.Method;

import org.cellang.console.view.ChartView;
import org.cellang.console.view.SimpleChartDataProvider;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;

public class SimpleChartView extends ChartView {

	public SimpleChartView(String title, int pageSize, EntitySessionFactory es, EntityConfig cfg, Method xGetMethod,
			Method yGetMethod) {
		super(title, new SimpleChartDataProvider(pageSize, es, cfg, xGetMethod, yGetMethod));
	}

}
