package org.cellang.console.view.table;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class DefaultTablePane extends JTable {
	static class DateCellRenderer extends DefaultTableCellRenderer.UIResource {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZZZ");

		public DateCellRenderer() {
			super();
		}

		@Override
		protected void setValue(Object value) {
			setText((value == null) ? "" : formatter.format(value));
		}
	}

	public DefaultTablePane(TableModel model) {
		super(model);
		this.setDefaultRenderer(Date.class, new DateCellRenderer());
	}

}
