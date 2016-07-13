package org.cellang.console.ops;

import org.cellang.console.OperationContext;
import org.cellang.console.ViewsPane;
import org.cellang.console.view.ChartView;

public class ChartOp {

	private int xCol;
	private int yCol;
	private OperationContext oc;

	public ChartOp(OperationContext oc, int xCol, int yCol) {
		this.oc = oc;
		this.xCol = xCol;
		this.yCol = yCol;
	}

	public void execute() {
		ViewsPane views = oc.getViewManager();
		ChartView view = new ChartView("ChartView");
		views.addView(view, true);
	}
}
