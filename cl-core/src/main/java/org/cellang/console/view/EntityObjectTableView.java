package org.cellang.console.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.EntityObjectSource;
import org.cellang.console.control.EntityObjectSourceListener;
import org.cellang.console.control.Filterable;
import org.cellang.core.entity.EntityConfig;
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
	TableColumnAdjuster tableColumnAdjuster;

	public EntityObjectTableView(EntityConfig cfg, EntitySessionFactory es, int pageSize) {
		this.cfg = cfg;
		this.entityService = es;

		model = new EntityQueryTableModel(this.entityService, cfg, pageSize);

		// TODO remove this and adjustColumns when double click the header of
		// table.
		model.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				tableColumnAdjuster.adjustColumns();
			}
		});

		this.table = new JTable(model);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableColumnAdjuster = new TableColumnAdjuster(this.table, 5);
		tableColumnAdjuster.setOnlyAdjustLarger(true);//
		// tableColumnAdjuster.setDynamicAdjustment(true);//
		tableColumnAdjuster.adjustColumns();
		this.setViewportView(this.table);
		this.title = "Entities-" + cfg.getTableName();
		this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		this.table.setFillsViewportHeight(true);
		// Note, JTable must be added to a JScrollPane,otherwise the header not
		// showing.
		this.model.nextPage();
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
