package org.cellang.viewsframework.control;

import org.cellang.viewsframework.table.ColumnDefine;

public interface ColumnSelector<T> {

	public void addColumnSelectionListener(SelectionListener<ColumnDefine<T>> esl);

}
