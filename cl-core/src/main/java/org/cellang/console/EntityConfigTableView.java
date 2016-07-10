package org.cellang.console;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.cellang.core.entity.EntityConfig;

public class EntityConfigTableView extends JScrollPane implements View {

	public static class Model extends AbstractTableModel {
		List<EntityConfig> list;

		public Model(List<EntityConfig> list) {
			this.list = list;
		}

		@Override
		public int getRowCount() {

			return this.list.size();

		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object rt = null;
			EntityConfig ec = this.list.get(rowIndex);

			switch (columnIndex) {
			case 0:
				rt = String.valueOf(rowIndex);
				break;
			case 1:
				rt = ec == null ? null : ec.getEntityClass();
				break;

			case 2:
				rt = ec == null ? null : ec.getTableName();
				break;

			}
			return rt;
		}

	}

	protected String title;

	protected JTable table;

	public EntityConfigTableView(List<EntityConfig> list) {

		this.table = new JTable(new Model(list));
		this.setViewportView(this.table);
		this.title = "EntityConfig";
		this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		this.table.setFillsViewportHeight(true);
		// Note, JTable must be added to a JScrollPane,otherwise the header not
		// showing.
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getTitle() {
		return title;
	}

}
