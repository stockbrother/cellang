package org.cellang.console.view.report;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.table.ColumnDefine;
import org.cellang.console.view.table.TableDataView;
import org.cellang.core.entity.AbstractReportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying report item for specified report date and corp.
 * 
 * @author wu
 *
 */
public class ReportTableView<T extends AbstractReportEntity> extends TableDataView<ReportRow> implements HasActions {

	static final Logger LOG = LoggerFactory.getLogger(ReportTableView.class);
	OperationContext oc;
	ReportRowChartView chartView;
	ReportRowChartDataProvider chartDp;
	ReportRow selectedRow;

	public ReportTableView(OperationContext oc, Class<T> cls, int years, String corpId) {
		super("Report of " + cls.getName(), oc, new ReportTableDataProvider<T>(oc, cls, years, corpId));
		this.oc = oc;
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		return al;
	}

	@Override
	protected void onColumnSelected(Integer col, ColumnDefine<ReportRow> colDef) {
		super.onColumnSelected(col, colDef);
		this.onRowOrColSelected(this.selectedRow);
	}

	@Override
	protected void onRowSelected(Integer row, ReportRow rowObj) {
		super.onRowSelected(row, rowObj);
		this.selectedRow = rowObj;
		this.onRowOrColSelected(this.selectedRow);
	}

	protected void onRowOrColSelected(ReportRow rowObj) {
		if (rowObj == null) {
			return;
		}
		if (this.chartView == null) {
			chartDp = new ReportRowChartDataProvider();
			ReportRowChartView cv = new ReportRowChartView(oc, chartDp);
			this.oc.getViewManager().addView(2, cv, true);
			this.chartView = cv;
		}
		boolean add = this.chartDp.addReportRow(rowObj);
		if (!add) {
			((ReportTableDataProvider) this.dp).changeCollapse(rowObj.getKey());
		}
	}

}
