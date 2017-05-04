package org.cellang.viewsframework.table;

import java.util.ArrayList;
import java.util.List;

import org.cellang.viewsframework.model.ColumnChangedEventSource;
import org.cellang.viewsframework.model.ColumnChangedListener;
import org.cellang.viewsframework.model.DataChangable;
import org.cellang.viewsframework.model.DataChangedListener;

public abstract class AbstractTableDataProvider<T>
		implements TableDataProvider<T>, DataChangable, ColumnChangedEventSource {
	protected List<AbstractColumn<T>> columnList = new ArrayList<>();

	List<DataChangedListener> dataChangedListenerList = new ArrayList<>();

	List<ColumnChangedListener> columnChangedListenerList = new ArrayList<>();

	public AbstractTableDataProvider() {

	}
	//
	// protected <T> void addDelagate(Class<? extends T> cls, T obj) {
	// Object obj2 = this.delagateMap.get(cls);
	// if (obj2 != null) {
	//
	// }
	// this.delagateMap.put(cls, obj);
	// }

	@Override
	public void addDataChangeListener(DataChangedListener l) {
		this.dataChangedListenerList.add(l);
	}

	@Override
	public void addColumnListener(ColumnChangedListener l) {
		this.columnChangedListenerList.add(l);
	}

	protected void fireTableDataChanged() {
		for (DataChangedListener l : this.dataChangedListenerList) {
			l.onDataChanged();
		}
	}

	protected void fireColumnChanged() {
		for (ColumnChangedListener l : this.columnChangedListenerList) {
			l.onColumnChanged();
		}
	}

	@Override
	public ColumnDefine<T> getColumn(int idx) {
		return this.columnList.get(idx);
	}

	@Override
	public int getColumnCount() {
		return this.columnList.size();
	}

	public int getRowNumber(int rowIndex) {
		return rowIndex + 1;
	}

}
