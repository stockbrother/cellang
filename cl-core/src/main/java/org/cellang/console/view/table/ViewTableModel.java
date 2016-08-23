package org.cellang.console.view.table;

import javax.swing.table.AbstractTableModel;

import org.cellang.console.HasDelagates;
import org.cellang.console.model.ColumnChangedEventSource;
import org.cellang.console.model.ColumnChangedListener;
import org.cellang.console.model.DataChangable;
import org.cellang.console.model.DataChangedListener;

public class ViewTableModel<T> extends AbstractTableModel implements ColumnChangedListener, DataChangedListener {
	TableDataProvider<T> dp;

	ViewTableModel(TableDataProvider<T> dp) {
		this.dp = dp;
		if (dp instanceof HasDelagates) {
			HasDelagates has = (HasDelagates) dp;
			ColumnChangedEventSource cc = has.getDelegate(ColumnChangedEventSource.class);
			if (cc != null) {
				cc.addColumnListener(this);
			}
			DataChangable dc = has.getDelegate(DataChangable.class);
			if (dc != null) {
				dc.addDataChangeListener(this);
			}
		}
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
	public String getColumnName(int column) {
		return dp.getColumn(column).getColumnName(column);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		ColumnDefine<T> cd = dp.getColumn(columnIndex);
		Class<?> rt = cd.getValueRenderingClass();
		if (rt == null) {
			throw new RuntimeException("bug,column class is null for column define:" + cd);
		}
		return rt;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		T ro = dp.getRowObject(rowIndex);
		return dp.getColumn(columnIndex).getValue(rowIndex, ro);

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