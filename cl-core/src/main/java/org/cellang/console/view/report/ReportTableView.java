package org.cellang.console.view.report;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.table.ColumnDefine;
import org.cellang.console.view.table.TableDataView;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.metrics.ReportConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying report item for specified report date and corp.
 * 
 * @author wu
 *
 */
public class ReportTableView extends TableDataView<ReportRow> implements HasActions {

	static final Logger LOG = LoggerFactory.getLogger(ReportTableView.class);
	OperationContext oc;
	ReportRowChartView chartView;
	ReportRowChartDataProvider chartDp;
	public ReportTableView(OperationContext oc, ReportConfig rptCfg, EntitySessionFactory es, int years,
			String corpId) {
		super("Report of " + rptCfg.getReportEntityConfig().getTableName(),
				new ReportTableDataProvider(rptCfg, es, years, corpId));
		this.oc = oc;
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		return al;
	}

	@Override
	protected void onColumnSelected(Integer col, ColumnDefine<ReportRow> colDef) {
		super.onColumnSelected(col, colDef);

	}

	@Override
	protected void onRowSelected(Integer row, ReportRow rowObj) {
		super.onRowSelected(row, rowObj);
		if (this.chartView == null) {
			chartDp = new ReportRowChartDataProvider();			
			ReportRowChartView cv = new ReportRowChartView(chartDp);
			this.oc.getViewManager().addView(cv, false);
			this.chartView = cv;
		}
		this.chartDp.setReportRow(rowObj);

	}

}
