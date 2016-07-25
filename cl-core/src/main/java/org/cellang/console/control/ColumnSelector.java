package org.cellang.console.control;

import org.cellang.console.view.table.ColumnDefine;

public interface ColumnSelector<T> {

	public void addColumnSelectionListener(SelectionListener<ColumnDefine<T>> esl);

}
