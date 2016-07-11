package org.cellang.console;

import java.awt.Component;
import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityObjectTableView extends JScrollPane implements View {

	private static final Logger LOG = LoggerFactory.getLogger(EntityObjectTableView.class);

	public static class Model extends AbstractTableModel {
		List<? extends EntityObject> list;
		EntityConfig cfg;
		List<Method> getMethodList;

		public Model(EntityConfig cfg, List<? extends EntityObject> list) {
			this.cfg = cfg;
			this.list = list;
			this.getMethodList = this.cfg.getGetMethodList();
		}

		@Override
		public int getRowCount() {
			return this.list.size();
		}

		@Override
		public int getColumnCount() {
			return this.getMethodList.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {

			EntityObject ec = this.list.get(rowIndex);
			Method getM = this.getMethodList.get(columnIndex);
			Object rt;
			try {
				rt = getM.invoke(ec);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			if (LOG.isTraceEnabled()) {
				LOG.trace("getValueAt,method:" + getM.getName() + ",rt:" + rt);//
			}
			return rt;
		}

	}

	protected String title;

	protected JTable table;

	public EntityObjectTableView(EntityConfig cfg, List<? extends EntityObject> list) {

		this.table = new JTable(new Model(cfg, list));
		this.setViewportView(this.table);
		this.title = "EntityObject";
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
