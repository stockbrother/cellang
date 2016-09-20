package org.cellang.console.view.report;

import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.lang.Visitor;
import org.cellang.console.format.ReportItemLocator;
import org.cellang.console.format.ReportItemLocators;
import org.cellang.console.view.table.AbstractColumn;
import org.cellang.console.view.table.AbstractTableDataProvider;
import org.cellang.console.view.table.LineNumberColumn;
import org.cellang.core.entity.AbstractReportEntity;
import org.cellang.core.metrics.ReportConfig;

public class ReportTemplateTableDataProvider extends AbstractTableDataProvider<ReportTemplateRow> {

	static ReportItemLocators RIL = ReportItemLocators.getInstance();

	ReportItemLocatorFilter filter = new ReportItemLocatorFilter();
	ReportItemLocators.Group template;
	ReportConfig reportConfig;
	List<ReportTemplateRow> backList;
	public ReportTemplateTableDataProvider(ReportConfig rc) {
		this.reportConfig = rc;
		
		this.template = ReportItemLocators.getInstance().get((Class<? extends AbstractReportEntity>)rc.getReportEntityConfig().getEntityClass());		

		this.columnList.add(new LineNumberColumn<ReportTemplateRow>(this));
		this.columnList.add(new ReportTemplateRowKeyColumn(template, this, filter));

		this.refresh();
	}

	/**
	 * Template
	 * 
	 * @return
	 */
	private List<ReportTemplateRow> newReportTemplateRowListFromLocaltors() {
		List<ReportTemplateRow> rL = new ArrayList<>();
		List<ReportItemLocator> locL = new ArrayList<>();

		template.getRoot().forEach(new Visitor<ReportItemLocator>() {

			@Override
			public void visit(ReportItemLocator t) {
				if (filter.accept(t)) {
					locL.add(t);
				}
			}
		}, false);

		for (ReportItemLocator ri : locL) {
			ReportTemplateRow rr = new ReportTemplateRow(this.reportConfig, ri.getKey(), ri);
			rL.add(rr);
		}
		return rL;
	}

	private void refresh() {
		
		// create a sorted empty ReportTemplateRow.
		List<ReportTemplateRow> rL = this.newReportTemplateRowListFromLocaltors();
		
		this.backList = rL;
		AbstractColumn<ReportTemplateRow> ac = this.columnList.get(this.columnList.size() - 1);

		this.fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		if (this.backList == null) {
			return 0;
		}
		return this.backList.size();
	}

	@Override
	public ReportTemplateRow getRowObject(int idx) {
		if (idx < 0 || this.backList == null || idx > this.backList.size() - 1) {
			return null;
		}
		return this.backList.get(idx);
	}

	@Override
	public int getRowNumber(int rowIndex) {
		return rowIndex;
	}

	public void changeCollapse(String key) {
		this.filter.changeCollapse(key);
		this.refresh();
	}

}
