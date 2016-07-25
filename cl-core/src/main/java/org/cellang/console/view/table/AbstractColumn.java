package org.cellang.console.view.table;

public abstract class AbstractColumn<T> {
	AbstractTableDataProvider<T> model;
	String name;

	AbstractColumn(AbstractTableDataProvider<T> model, String name) {
		this.model = model;
		this.name = name;
	}

	public abstract Object getValue(int rowIndex);

	// if the column is filterable, action ui will add conditional input
	// argument for filtering of the data table based on this column.
	public abstract String getFilterableColumn();

	public abstract Class<?> getValueRenderingClass();

	public String getDisplayName() {

		return name;

	}
}