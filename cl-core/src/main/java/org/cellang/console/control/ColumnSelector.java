package org.cellang.console.control;

import org.cellang.console.view.table.AbstractColumn;

public interface ColumnSelector<T> {

	public void addColumnSelectionListener(SelectionListener<AbstractColumn<T>> esl);

}
