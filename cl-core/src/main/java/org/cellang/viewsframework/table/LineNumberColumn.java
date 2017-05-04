package org.cellang.viewsframework.table;

public class LineNumberColumn<T> extends AbstractColumn<T> {

	public LineNumberColumn(AbstractTableDataProvider<T> model) {
		super(model, "LN.");
	}

	@Override
	public Object getValue(int rowIndex, T ro) {
		return model.getRowNumber(rowIndex);
	}

	@Override
	public String getFilterableColumn() {
		return null;
	}

	@Override
	public Class<?> getValueRenderingClass() {
		return Long.class;
	}

}