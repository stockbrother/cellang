package org.cellang.console.model;

public abstract class AbstractTableDataProvider<T> implements TableDataProvider<T> {

	@Override
	public String getColumnName(int column) {

		String result = "";
		for (; column >= 0; column = column / 26 - 1) {
			result = (char) ((char) (column % 26) + 'A') + result;
		}
		return result;
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {

		return null;
	}

}
