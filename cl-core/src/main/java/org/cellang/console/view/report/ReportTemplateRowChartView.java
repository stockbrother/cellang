package org.cellang.console.view.report;

import java.text.DecimalFormat;

import org.cellang.console.chart.XLabelRenderer;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.chart.ChartView;
import org.cellang.console.view.report.ReportTemplateRowChartDataProvider.Scope;

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
