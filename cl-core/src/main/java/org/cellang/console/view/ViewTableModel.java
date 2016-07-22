package org.cellang.console.view;

import javax.swing.table.AbstractTableModel;

import org.cellang.console.model.ColumnChangedEventSource;
import org.cellang.console.model.ColumnChangedListener;
import org.cellang.console.model.DataChangable;
import org.cellang.console.model.DataChangedListener;
import org.cellang.console.model.TableDataProvider;

public class ViewTableModel<T> extends AbstractTableModel implements ColumnChangedListener, DataChangedListener {
	TableDataProvider<T> dp;

	ViewTableModel(TableDataProvider<T> dp) {
		this.dp = dp;
		ColumnChangedEventSource cc = dp.getDelegate(ColumnChangedEventSource.class);
		if (cc != null) {
			cc.addColumnListener(this);
		}
		DataChangable dc = dp.getDelegate(DataChangable.class);
		if (dc != null) {
			dc.addDataChangeListener(this);
		}
	}

	@Override
	public Class<?> getColumnClass(int index) {
		return dp.getColumnClass(index);
	}

	@Override
	public int getRowCount() {

		return dp.getRowCount();
	}

	@Override
	public int getColumnCount() {

		return dp.getColumnCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		return dp.getValueAt(rowIndex, columnIndex);

	}

	@Override
	public void onDataChanged() {
		this.fireTableDataChanged();
	}

	@Override
	public void onColumnChanged() {
		this.fireTableStructureChanged();
	}

}