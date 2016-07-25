package org.cellang.console.view.table;

class LineNumberColumn<T> extends AbstractColumn<T> {

	LineNumberColumn(AbstractTableDataProvider<T> model) {
		super(model, "LN.");
	}

	@Override
	public Object getValue(int rowIndex,T ro) {
		return model.getRowNumber(rowIndex);
	}

	@Override
	public String getFilterableColumn() {
		return null;
	}

	@Override
	public Class<?> getValueRenderingClass() {
		return String.class;
	}

}