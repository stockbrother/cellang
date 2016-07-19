package org.cellang.console.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.console.control.DrillDowable;
import org.cellang.console.ops.EntityConfigManager;
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
	EntityConfigManager entityConfigManager;
	protected JTable table;
	List<EntityConfig> list;
	OperationContext oc;
	TableColumnAdjuster tableColumnAdjuster;
	String id;
	public EntityConfigTableView(OperationContext oc, List<EntityConfig> list) {
		this.id = UUIDUtil.randomStringUUID();
		this.oc = oc;
		this.entityConfigManager = oc.getEntityConfigManager();
		this.list = list;
		this.table = new JTable(new TableModel(list));
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableColumnAdjuster = new TableColumnAdjuster(this.table, 5);
		// tableColumnAdjuster.setDynamicAdjustment(true);//
		tableColumnAdjuster.adjustColumns();
		this.setViewportView(this.table);
		this.title = "Factory-Entities";
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
		// int idx0 = e.getFirstIndex();
		// int idx1 = e.getLastIndex();
		int idx = this.table.getSelectedRow();
		EntityConfig ec = this.list.get(idx);
		this.entityConfigManager.setSelectedEntityConfig(ec);//
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

	@Override
	public String getId() {
		return id;
	}

}
