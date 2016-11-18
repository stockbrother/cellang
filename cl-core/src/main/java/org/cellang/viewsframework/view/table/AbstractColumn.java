package org.cellang.viewsframework.view.table;

public abstract class AbstractColumn<T> implements ColumnDefine<T> {
	protected AbstractTableDataProvider<T> model;
	protected String name;

	public AbstractColumn(AbstractTableDataProvider<T> model, String name) {
		this.model = model;
		this.name = name;
	}

	public String getFilterableColumn() {
		return null;
	}
	// if the column is filterable, action ui will add conditional input
	// argument for filtering of the data table based on this column.

	public Class<?> getValueRenderingClass() {
		return Object.class;
	}

	public String getDisplayName() {

		return name;

	}

	@Override
	public String getColumnName(int column) {

		String result = "";
		for (; column >= 0; column = column / 26 - 1) {
			result = (char) ((char) (column % 26) + 'A') + result;
		}
		result += "(" + this.name + ")";
		return result;
	}

}