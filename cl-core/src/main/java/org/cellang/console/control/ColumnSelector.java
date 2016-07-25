package org.cellang.console.control;

import org.cellang.console.view.table.ColumnDefine;

public interface ColumnSelector {

	public void addColumnSelectionListener(SelectionListener<ColumnDefine> esl);

}
