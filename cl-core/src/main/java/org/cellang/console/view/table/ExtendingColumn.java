package org.cellang.console.view.table;

import org.cellang.console.ext.ExtendingPropertyDefine;
import org.cellang.core.entity.EntityObject;

class ExtendingColumn extends AbstractColumn<EntityObject> {
	ExtendingPropertyDefine calculator;

	ExtendingColumn(EntityObjectTableDataProvider model, ExtendingPropertyDefine calculator) {
		super(model, calculator.getKey());
		this.calculator = calculator;
	}

	@Override
	public Object getValue(int rowIndex) {

		EntityObject ec = model.getRowObject(rowIndex);
		if (ec == null) {
			return null;
		}
		return this.calculator.getValue(ec);
	}

	@Override
	public String getFilterableColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getValueRenderingClass() {
		return calculator.getValueClass();
	}

}