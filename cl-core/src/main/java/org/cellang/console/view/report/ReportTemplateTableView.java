package org.cellang.console.view.report;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.console.format.ReportItemLocators;
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
public class ReportTemplateTableView<T extends AbstractReportEntity> extends TableDataView<ReportTemplateRow> implements HasActions {

	static final Logger LOG = LoggerFactory.getLogger(ReportTemplateTableView.class);
	OperationContext oc;
	
	ReportTemplateRow selectedRow;

	public ReportTemplateTableView(OperationContext oc, Class<T> cls, ReportItemLocators.Group template) {
		super("Report Template",
				new ReportTemplateTableDataProvider<T>(cls, template));
		this.oc = oc;
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		return al;
	}

	@Override
	protected void onColumnSelected(Integer col, ColumnDefine<ReportTemplateRow> colDef) {
		super.onColumnSelected(col, colDef);
		this.onRowOrColSelected(this.selectedRow);
	}

	@Override
	protected void onRowSelected(Integer row, ReportTemplateRow rowObj) {
		super.onRowSelected(row, rowObj);
		this.selectedRow = rowObj;
		this.onRowOrColSelected(this.selectedRow);
	}

	protected void onRowOrColSelected(ReportTemplateRow rowObj) {
		if (rowObj == null) {
			return;
		}		
	}

}