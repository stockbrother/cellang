package org.cellang.console.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.cellang.console.control.DrillDowable;
import org.cellang.console.ops.OperationContext;
import org.cellang.core.entity.EntityConfig;

public class EntityConfigTableView extends JScrollPane implements View, DrillDowable {

	public static class TableModel extends AbstractTableModel {
		List<EntityConfig> list;

		public TableModel(List<EntityConfig> list) {
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
	List<EntityConfig> list;
	OperationContext oc;

	public EntityConfigTableView(OperationContext oc, List<EntityConfig> list) {
		this.oc = oc;
		this.list = list;
		this.table = new JTable(new TableModel(list));
		this.setViewportView(this.table);
		this.title = "EntityConfig";
		this.table.setPreferredScrollableViewportSize(new Dimension(1000, 300));
		this.table.setFillsViewportHeight(true);
		this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				EntityConfigTableView.this.onSelectEvent(e);
			}
		});
		// Note, JTable must be added to a JScrollPane,otherwise the header not
		// showing.
	}

	protected void onSelectEvent(ListSelectionEvent e) {
		int idx = e.getFirstIndex();
		EntityConfig ec = this.list.get(idx);
		this.oc.setSelectedEntityConfig(ec);//
	}

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (DrillDowable.class.equals(cls)) {
			return (T) this;
		}
		return null;
	}

	@Override
	public void drillDown() {
		this.oc.list();
	}

}
