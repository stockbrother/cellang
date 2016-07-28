package org.cellang.console.view.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.HasDelagates;
import org.cellang.console.model.ColumnChangedEventSource;
import org.cellang.console.model.ColumnChangedListener;
import org.cellang.console.model.DataChangable;
import org.cellang.console.model.DataChangedListener;

public abstract class AbstractTableDataProvider<T>
		implements HasDelagates ,TableDataProvider<T>, DataChangable, ColumnChangedEventSource {
	protected List<AbstractColumn<T>> columnList = new ArrayList<>();

	List<DataChangedListener> dataChangedListenerList = new ArrayList<>();

	List<ColumnChangedListener> columnChangedListenerList = new ArrayList<>();

	protected Map<Class, Object> delagateMap = new HashMap<Class, Object>();

	public AbstractTableDataProvider() {
		this.addDelagate(DataChangable.class, this);
		this.addDelagate(ColumnChangedEventSource.class, this);
	}

	protected <T> void addDelagate(Class<? extends T> cls, T obj) {
		Object obj2 = this.delagateMap.get(cls);
		if (obj2 != null) {

		}
		this.delagateMap.put(cls, obj);
	}

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
	public AbstractColumn<T> getColumn(int idx){
		return this.columnList.get(idx);
	}

	@Override
	public int getColumnCount() {
		return this.columnList.size();
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		return (T) this.delagateMap.get(cls);
	}

	public abstract int getRowNumber(int rowIndex);

}
