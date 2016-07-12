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
import org.cellang.core.entity.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityObjectTableView extends JScrollPane implements View, EntityObjectSource, DataPageQuerable {

	private static final Logger LOG = LoggerFactory.getLogger(EntityObjectTableView.class);

	public static class Model extends AbstractTableModel {
		int pageSize;
		List<? extends EntityObject> list;
		EntityConfig cfg;
		List<Method> getMethodList;

		public Model(EntityConfig cfg, int pageSize) {
			this.cfg = cfg;
			this.pageSize = pageSize;
			this.getMethodList = this.cfg.getGetMethodList();
		}

		@Override
		public int getRowCount() {

			return this.pageSize;

		}

		@Override
		public int getColumnCount() {
			return this.getMethodList.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (this.list == null) {
				return null;
			}
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

	protected EntityConfig cfg;

	protected int pageSize;

	protected int pageNumber = -1;

	protected EntityService entityService;
	Model model;

	public EntityObjectTableView(EntityConfig cfg, EntityService es, int pageSize) {
		this.cfg = cfg;
		this.entityService = es;
		this.pageSize = pageSize;
		model = new Model(cfg, pageSize);

		this.table = new JTable(model);
		this.setViewportView(this.table);
		this.title = "EntityObject";
		this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		this.table.setFillsViewportHeight(true);
		// Note, JTable must be added to a JScrollPane,otherwise the header not
		// showing.
		this.nextPage();
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
		if (cls.equals(EntityObjectSource.class)) {
			return (T) this;
		} else if (cls.equals(DataPageQuerable.class)) {
			return (T) this;
		}

		return null;
	}

	@Override
	public void addEntityObjectSourceListener(EntityObjectSourceListener esl) {

	}

	@Override
	public void prePage() {
		this.pageNumber--;
		this.query();
	}

	private void query() {
		List<? extends EntityObject> el = this.entityService.query(cfg.getEntityClass()).limit(this.pageSize)
				.executeQuery();
		model.list = el;
	}

	@Override
	public void nextPage() {
		this.pageNumber++;
		this.query();
	}

}
