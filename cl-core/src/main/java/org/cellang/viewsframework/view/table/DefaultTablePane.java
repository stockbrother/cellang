package org.cellang.viewsframework.view.table;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 * 
 * @author wu
 *
 */
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

	static class BigDecimalRenderer extends DefaultTableCellRenderer.UIResource {
		NumberFormat formatter = NumberFormat.getInstance();

		public BigDecimalRenderer() {
			super();
			setHorizontalAlignment(JLabel.RIGHT);
		}

		public void setValue(Object value) {
			setText((value == null) ? "" : formatter.format(value));
		}
	}

	public DefaultTablePane(TableModel model) {
		super(model);
		this.setDefaultRenderer(Date.class, new DateCellRenderer());
		this.setDefaultRenderer(BigDecimal.class, new BigDecimalRenderer());//
	}

}
