package org.cellang.console.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.EntityObjectSource;
import org.cellang.console.control.EntityObjectSourceListener;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityObjectTableView extends JScrollPane implements View, EntityObjectSource, DataPageQuerable {

	static final Logger LOG = LoggerFactory.getLogger(EntityObjectTableView.class);

	protected String title;

	protected JTable table;

	protected EntityConfig cfg;

	protected int pageSize;

	protected int pageNumber = -1;

	protected EntitySessionFactory entityService;
	EntityQueryTableModel model;

	public EntityObjectTableView(EntityConfig cfg, EntitySessionFactory es, int pageSize) {
		this.cfg = cfg;
		this.entityService = es;
		this.pageSize = pageSize;
		model = new EntityQueryTableModel(cfg, pageSize);

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
		if (this.pageNumber == 0) {
			return;
		}
		this.pageNumber--;
		this.query();
	}

	private void query() {
		int offset = this.pageNumber * this.pageSize;
		List<? extends EntityObject> el = new EntityQuery<>(cfg).offset(offset).limit(this.pageSize)
				.execute(this.entityService);
		model.list = el;

		this.updateUI();
	}

	@Override
	public void nextPage() {
		if (model.list != null && model.list.size() < this.pageSize) {
			return;
		}
		this.pageNumber++;
		this.query();

	}

}
