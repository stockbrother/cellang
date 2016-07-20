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
import org.cellang.console.control.HasActions;
import org.cellang.console.control.RowSelector;
import org.cellang.console.control.SelectionListener;
import org.cellang.console.model.TableDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying entity list result.
 * 
 * @author wu
 *
 */
public class TableDataView<T> extends JScrollPane implements View, RowSelector<T> {

	static final Logger LOG = LoggerFactory.getLogger(TableDataView.class);

	protected String title;

	protected JTable table;

	ViewTableModel<T> model;
	TableColumnAdjuster tableColumnAdjuster;

	String id;
	TableDataProvider<T> dp;
	List<SelectionListener<T>> rowSelectionListenerList = new ArrayList<>();

	public TableDataView(String title, TableDataProvider<T> dp) {

		this.id = UUIDUtil.randomStringUUID();
		this.dp = dp;
		model = new ViewTableModel<T>(dp);

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
		this.title = title;

		this.table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		this.table.setFillsViewportHeight(true);
		this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				TableDataView.this.onSelectEvent(e);
			}
		});
		// Note, JTable must be added to a JScrollPane,otherwise the header not
		// showing.
	}

	private void onSelectEvent(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			// ignore if not finalized selected.
			return;
		}

		// int idx0 = e.getFirstIndex();
		// int idx1 = e.getLastIndex();
		int idx = this.table.getSelectedRow();
		T row = this.dp.getRowObject(idx);
		this.onSelected(row);//
	}

	protected void onSelected(T row) {
		for (SelectionListener<T> sl : this.rowSelectionListenerList) {
			sl.onSelected(row);
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
		if (cls.equals(TableDataProvider.class)) {
			return (T) this.dp;
		} else if (cls.equals(HasActions.class)) {
			if (this instanceof HasActions) {
				return (T) this;
			}
		} else if (cls.equals(RowSelector.class)) {
			return (T) this;
		}

		return null;
	}

	@Override
	public String getId() {

		return id;
	}

	@Override
	public void addSelectionListener(SelectionListener<T> esl) {
		this.rowSelectionListenerList.add(esl);
	}

}
