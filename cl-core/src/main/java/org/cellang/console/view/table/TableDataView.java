package org.cellang.console.view.table;

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
import org.cellang.console.control.ColumnSelector;
import org.cellang.console.control.HasActions;
import org.cellang.console.control.RowSelector;
import org.cellang.console.control.SelectionListener;
import org.cellang.console.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying entity list result.
 * 
 * @author wu
 *
 */
public class TableDataView<T> extends JScrollPane implements View, RowSelector<T>, ColumnSelector<T> {

	static final Logger LOG = LoggerFactory.getLogger(TableDataView.class);

	protected String title;

	protected DefaultTablePane table;

	ViewTableModel<T> model;
	TableColumnAdjuster tableColumnAdjuster;

	String id;
	protected TableDataProvider<T> dp;
	List<SelectionListener<T>> rowSelectionListenerList = new ArrayList<>();
	List<SelectionListener<ColumnDefine<T>>> columnSelectionListenerList = new ArrayList<>();

	T selected;

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

		this.table = new DefaultTablePane(model);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// this.table.setDefaultRenderer(Object.class, renderer);
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

				TableDataView.this.onRowSelectEvent(e);
			}
		});
		this.table.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				TableDataView.this.onColumnSelectEvent(e);
			}
		});
		// Note, JTable must be added to a JScrollPane,otherwise the header not
		// showing.
	}

	private void onColumnSelectEvent(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			// ignore if not finalized selected.
			return;
		}

		// int idx0 = e.getFirstIndex();
		// int idx1 = e.getLastIndex();
		int idx = this.table.getSelectedColumn();
		ColumnDefine colDef = null;
		if (idx >= 0) {
			colDef = this.dp.getColumn(idx);
		}
		this.onColumnSelected(idx < 0 ? null : idx, colDef);//
	}

	private void onRowSelectEvent(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			// ignore if not finalized selected.
			return;
		}

		// int idx0 = e.getFirstIndex();
		// int idx1 = e.getLastIndex();
		int idx = this.table.getSelectedRow();
		T row = null;
		Integer integer = null;
		if (idx >= 0) {
			row = this.dp.getRowObject(idx);
			integer = idx;
		}
		this.onRowSelected(integer, row);//
	}

	protected void onColumnSelected(Integer col, ColumnDefine<T> colDef) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("columnSelected:" + col);
		}

		for (SelectionListener<ColumnDefine<T>> sl : this.columnSelectionListenerList) {
			sl.onSelected(colDef);
		}

	}

	protected void onRowSelected(Integer row, T rowObj) {
		this.selected = rowObj;
		if (LOG.isDebugEnabled()) {
			LOG.debug("rowSelected:" + row);
		}
		for (SelectionListener<T> sl : this.rowSelectionListenerList) {
			sl.onSelected(rowObj);
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
	public void addRowSelectionListener(SelectionListener<T> esl) {
		this.rowSelectionListenerList.add(esl);
	}

	@Override
	public void addColumnSelectionListener(SelectionListener<ColumnDefine<T>> esl) {
		this.columnSelectionListenerList.add(esl);
	}

}
