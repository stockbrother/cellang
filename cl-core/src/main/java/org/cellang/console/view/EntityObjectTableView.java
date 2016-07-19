package org.cellang.console.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.EntityConfigControl;
import org.cellang.console.control.EntityObjectSelectionListener;
import org.cellang.console.control.EntityObjectSelector;
import org.cellang.console.control.Filterable;
import org.cellang.console.control.HasActions;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying entity list result.
 * 
 * @author wu
 *
 */
public class EntityObjectTableView extends JScrollPane implements View, EntityObjectSelector {

	static final Logger LOG = LoggerFactory.getLogger(EntityObjectTableView.class);

	protected String title;

	protected JTable table;

	protected EntityConfig cfg;

	protected EntitySessionFactory entityService;
	EntityQueryTableModel model;
	TableColumnAdjuster tableColumnAdjuster;
	List<EntityObjectSelectionListener> listenerList = new ArrayList<>();
	EntityConfigControl entityConfigControl;
	String id;

	public EntityObjectTableView(EntityConfig cfg, EntityConfigControl ecc, EntitySessionFactory es, int pageSize) {
		this.id = UUIDUtil.randomStringUUID();
		this.cfg = cfg;
		this.entityConfigControl = ecc;
		this.entityService = es;

		model = new EntityQueryTableModel(this.entityService, cfg, pageSize, ecc.getColumnSorter());

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
		this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				EntityObjectTableView.this.onSelectEvent(e);
			}
		});
		// Note, JTable must be added to a JScrollPane,otherwise the header not
		// showing.
		this.model.nextPage();
	}

	@Override
	public void addEntityObjectSelectionListener(EntityObjectSelectionListener esl) {
		listenerList.add(esl);
	}

	protected void onSelectEvent(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			// ignore if not finalized selected.
			return;
		}
		// int idx0 = e.getFirstIndex();
		// int idx1 = e.getLastIndex();
		int idx = this.table.getSelectedRow();
		EntityObject eo = this.model.getEntityObject(idx);
		for (EntityObjectSelectionListener l : this.listenerList) {
			l.onEntitySelected(eo);//
		}
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
		if (cls.equals(EntityObjectSelector.class)) {
			return (T) this;
		} else if (cls.equals(DataPageQuerable.class)) {
			return (T) this.model;
		} else if (cls.equals(Filterable.class)) {
			return (T) this.model;
		} else if (cls.equals(HasActions.class)) {
			return (T) this.entityConfigControl.getDelegate(HasActions.class);
		}

		return null;
	}

	@Override
	public String getId() {

		return id;
	}

}
