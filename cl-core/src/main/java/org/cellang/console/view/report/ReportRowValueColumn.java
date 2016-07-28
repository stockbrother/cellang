package org.cellang.console.view.report;

import java.math.BigDecimal;

import org.cellang.console.view.table.AbstractColumn;
import org.cellang.console.view.table.AbstractTableDataProvider;
import org.cellang.core.entity.AbstractReportItemEntity;

public class ReportRowValueColumn extends AbstractColumn<ReportRow> {

	int valueColumnIndex;

	public ReportRowValueColumn(int valueColumnIndex, AbstractTableDataProvider<ReportRow> model) {
		super(model, "value");//
		this.valueColumnIndex = valueColumnIndex;
	}

	@Override
	public Object getValue(int rowIndex, ReportRow ec) {

		AbstractReportItemEntity eo = ec.get(this.valueColumnIndex);

		if (eo == null) {
			return null;
		}

		return eo.getValue();
	}

	@Override
	public Class<?> getValueRenderingClass() {
		return BigDecimal.class;
	}

}