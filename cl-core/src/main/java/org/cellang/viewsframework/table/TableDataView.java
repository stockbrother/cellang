package org.cellang.viewsframework.table;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.cellang.commons.util.UUIDUtil;
import org.cellang.viewsframework.AbstractView;
import org.cellang.viewsframework.HasDelegates;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.control.RowSelector;
import org.cellang.viewsframework.ops.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying entity list result.
 * 
 * @author wu
 *
 */
public class TableDataView<T> extends AbstractView implements HasDelegates {

	static final Logger LOG = LoggerFactory.getLogger(TableDataView.class);

	protected DefaultTablePane table;

	ViewTableModel<T> model;
	TableColumnAdjuster tableColumnAdjuster;

	String id;
	protected TableDataProvider<T> dp;

	public TableDataView(String title, OperationContext oc, TableDataProvider<T> dp) {
		super(title, oc);
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
	}

	protected void onRowSelected(Integer row, T rowObj) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("rowSelected:" + row);
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

}
