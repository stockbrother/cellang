package org.cellang.console.corpgrouping;

import java.util.ArrayList;
import java.util.List;

import org.cellang.console.HasDelegates;
import org.cellang.console.control.Refreshable;
import org.cellang.console.corpgrouping.MyFavoritesCorpListView.Row;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.table.AbstractColumn;
import org.cellang.console.view.table.AbstractTableDataProvider;
import org.cellang.console.view.table.ColumnDefine;
import org.cellang.console.view.table.LineNumberColumn;
import org.cellang.console.view.table.TableDataView;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.GroupCorpEntity;

public class MyFavoritesCorpListView extends TableDataView<Row> implements HasDelegates, Refreshable {
	public static class Row {
		GroupCorpEntity groupCorp;

		public Row(GroupCorpEntity e) {
			this.groupCorp = e;
		}

	}

	public static class GroupIdColumn extends AbstractColumn<Row> {

		public GroupIdColumn(AbstractTableDataProvider<Row> model) {
			super(model, "GroupId");
		}

		@Override
		public Object getValue(int rowIdx, Row rowObj) {
			return rowObj.groupCorp.getGroupId();
		}
	}

	public static class CorpIdColumn extends AbstractColumn<Row> {

		public CorpIdColumn(AbstractTableDataProvider<Row> model) {
			super(model, "CorpId");
		}

		@Override
		public Object getValue(int rowIdx, Row rowObj) {
			return rowObj.groupCorp.getCorpId();
		}
	}

	public static class DataProvider extends AbstractTableDataProvider<Row> implements Refreshable {
		OperationContext oc;
		List<Row> list = new ArrayList<>();

		public DataProvider(OperationContext oc) {
			this.oc = oc;
			this.columnList.add(new LineNumberColumn<Row>(this));
			this.columnList.add(new GroupIdColumn(this));
			this.columnList.add(new CorpIdColumn(this));
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
			EntityConfig cfg = oc.getEntityConfigFactory().get(GroupCorpEntity.class);
			List<GroupCorpEntity> el = new EntityQuery<GroupCorpEntity>(cfg).eq("groupId", GROUP_ID).orderBy("corpId")
					.execute(oc.getEntityService());
			for (GroupCorpEntity e : el) {
				this.list.add(new Row(e));
			}
			this.fireTableDataChanged();
		}

	}

	public static final String GROUP_ID = "my-favorites";

	private String selectedCorpId;

	public MyFavoritesCorpListView(OperationContext oc) {
		super("MyFavorites", oc, new DataProvider(oc));
		this.refresh();
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (cls.equals(Refreshable.class)) {
			return (T) this;
		}
		return null;
	}

	@Override
	protected void onColumnSelected(Integer col, ColumnDefine<Row> colDef) {
		super.onColumnSelected(col, colDef);

	}

	@Override
	protected void onRowSelected(Integer row, Row rowObj) {
		super.onRowSelected(row, rowObj);
		this.selectedCorpId = rowObj.groupCorp.getCorpId();
		// open view
		CorpInfoView view = new CorpInfoView(oc);
		oc.getViewManager().addView(1, view, true);
		view.update(this.selectedCorpId);
	}

	@Override
	public void refresh() {
		DataProvider dp = (DataProvider) this.dp;
		dp.refresh();
	}
}
