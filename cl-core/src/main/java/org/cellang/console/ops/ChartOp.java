package org.cellang.console.ops;

import java.lang.reflect.Method;
import java.util.List;

import org.cellang.console.view.ChartView;
import org.cellang.console.view.ViewsPane;
import org.cellang.core.entity.EntityConfig;

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
		ViewsPane views = oc.getViewManager();
		EntityConfig ec = oc.getSelectedEntityConfig();
		List<Method> methodList = ec.getGetMethodList();
		Method xM = methodList.get(this.xCol);
		Method yM = methodList.get(this.yCol);

		ChartView view = new SimpleChartView("ChartView", 100, this.oc.getEntityService(), ec, xM, yM);

		views.addView(view, true);// TODO Auto-generated method stub
		return null;
	}
}
