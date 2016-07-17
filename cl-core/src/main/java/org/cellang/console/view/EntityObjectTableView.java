package org.cellang.console.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.EntityObjectSource;
import org.cellang.console.control.EntityObjectSourceListener;
import org.cellang.console.control.Filterable;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying entity list result.
 * 
 * @author wu
 *
 */
public class EntityObjectTableView extends JScrollPane implements View, EntityObjectSource {

	static final Logger LOG = LoggerFactory.getLogger(EntityObjectTableView.class);

	protected String title;

	protected JTable table;

	protected EntityConfig cfg;

	protected EntitySessionFactory entityService;
	EntityQueryTableModel model;

	public EntityObjectTableView(EntityConfig cfg, EntitySessionFactory es, int pageSize) {
		this.cfg = cfg;
		this.entityService = es;

		model = new EntityQueryTableModel(this.entityService, cfg, pageSize, new Runnable() {

			@Override
			public void run() {
				modelDataUpdated();
			}
		});

		this.table = new JTable(model);
		this.setViewportView(this.table);
		this.title = "EntityObject";
		this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		this.table.setFillsViewportHeight(true);
		// Note, JTable must be added to a JScrollPane,otherwise the header not
		// showing.
		this.model.nextPage();
	}

	private void modelDataUpdated() {
		this.updateUI();
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
			return (T) this.model;
		} else if (cls.equals(Filterable.class)) {
			return (T) this.model;
		}

		return null;
	}

	@Override
	public void addEntityObjectSourceListener(EntityObjectSourceListener esl) {

	}

}
