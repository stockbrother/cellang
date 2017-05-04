package org.cellang.viewsframework.ops;

import java.lang.reflect.Method;
import java.util.List;

import org.cellang.core.entity.EntityConfig;
import org.cellang.viewsframework.PerspectivePanel;
import org.cellang.viewsframework.chart.ChartView;

public class ChartOp extends ConsoleOp<Void> {

	private int xCol;
	private int yCol;
	private OperationContext oc;

	public ChartOp(OperationContext oc, int xCol, int yCol) {
		this.oc = oc;
		this.xCol = xCol;
		this.yCol = yCol;
	}

	@Override
	public Void execute(OperationContext oc) {
		PerspectivePanel views = oc.getViewManager();
		EntityConfig ec = null;//oc.getEntityConfigManager().getSelectedEntityConfig();
		List<Method> methodList = ec.getGetMethodList();
		Method xM = methodList.get(this.xCol);
		Method yM = methodList.get(this.yCol);

		ChartView view = new SimpleChartView("ChartView", 100, this.oc.getEntityService(), ec, xM, yM);

		views.addView(view, true);// TODO Auto-generated method stub
		return null;
	}
}
