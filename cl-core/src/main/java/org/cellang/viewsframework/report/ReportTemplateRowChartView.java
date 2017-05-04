package org.cellang.viewsframework.report;

import java.text.DecimalFormat;

import org.cellang.viewsframework.chart.ChartView;
import org.cellang.viewsframework.chart.XLabelRenderer;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.report.ReportTemplateRowChartDataProvider.Scope;

public class ReportTemplateRowChartView extends ChartView<Scope> {
	private DecimalFormat format = new DecimalFormat("#,##0.00");

	public ReportTemplateRowChartView(OperationContext oc, ReportTemplateRowChartDataProvider dp) {
		super("ReportTemplateRow", oc, dp);

		this.chart.setXLabelRenderer(new XLabelRenderer<Scope>() {

			@Override
			public String toDisplayValue(Scope value) {
				if (value == null) {
					return "null";
				}
				return format.format(value.from);
			}
		});
	}

}
