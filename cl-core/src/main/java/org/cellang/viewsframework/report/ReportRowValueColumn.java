package org.cellang.viewsframework.report;

import java.math.BigDecimal;

import org.cellang.core.entity.AbstractReportItemEntity;
import org.cellang.viewsframework.table.AbstractColumn;
import org.cellang.viewsframework.table.AbstractTableDataProvider;

public class ReportRowValueColumn extends AbstractColumn<ReportRow> {

	int valueColumnIndex;

	public ReportRowValueColumn(int valueColumnIndex,String name, AbstractTableDataProvider<ReportRow> model) {
		super(model, name);//
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