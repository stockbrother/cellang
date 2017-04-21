package org.cellang.corpsviewer;

import java.util.ArrayList;
import java.util.List;

import org.cellang.corpsviewer.CorpGroupTypeListView.Row;
import org.cellang.viewsframework.HasDelegates;
import org.cellang.viewsframework.control.Refreshable;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.table.AbstractColumn;
import org.cellang.viewsframework.view.table.AbstractTableDataProvider;
import org.cellang.viewsframework.view.table.LineNumberColumn;
import org.cellang.viewsframework.view.table.TableDataView;

public class CorpGroupTypeListView extends TableDataView<Row> implements HasDelegates {
	public static class Row {
		public Row(String string) {
			this.typeCode = string;
		}

		String typeCode;
		String description;
	}

	public static class GroupTypeCodeColumn extends AbstractColumn<Row> {

		public GroupTypeCodeColumn(AbstractTableDataProvider<Row> model) {
			super(model, "TypeCode");
		}

		@Override
		public Object getValue(int rowIdx, Row rowObj) {
			return rowObj.typeCode;
		}

	}

	public static class DataProvider extends AbstractTableDataProvider<Row> implements Refreshable {
		OperationContext oc;
		List<Row> list = new ArrayList<Row>();

		public DataProvider(OperationContext oc) {
			this.oc = oc;
			this.columnList.add(new LineNumberColumn<Row>(this));
			this.columnList.add(new GroupTypeCodeColumn(this));

		}

		@Override
		public int getRowCount() {
			return this.list.size();
		}

		@Override
		public Row getRowObject(int idx) {
			return this.list.get(idx);//
		}

		@Override
		public void refresh() {
			this.list.clear();

			this.list.add(new Row("MyFavorites"));

			this.fireTableDataChanged();
		}

	}

	Row selected;
	DeleteCorpGroupAction action;

	public CorpGroupTypeListView(OperationContext oc) {
		super("CorpGroupTypeList", oc, new DataProvider(oc));

		((DataProvider) this.dp).refresh();
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		return null;
	}

	@Override
	protected void onRowSelected(Integer row, Row rowObj) {
		this.selected = rowObj;

	}

	public Row getSelectedCorpGroup() {
		return this.selected;
	}

}
