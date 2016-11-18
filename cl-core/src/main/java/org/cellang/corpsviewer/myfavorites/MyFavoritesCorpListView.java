package org.cellang.corpsviewer.myfavorites;

import java.util.ArrayList;
import java.util.List;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.GroupCorpEntity;
import org.cellang.corpsviewer.MenuBar;
import org.cellang.corpsviewer.myfavorites.MyFavoritesCorpListView.Row;
import org.cellang.viewsframework.HasDelegates;
import org.cellang.viewsframework.control.Refreshable;
import org.cellang.viewsframework.corpgrouping.CorpInfoView;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.table.AbstractColumn;
import org.cellang.viewsframework.view.table.AbstractTableDataProvider;
import org.cellang.viewsframework.view.table.ColumnDefine;
import org.cellang.viewsframework.view.table.LineNumberColumn;
import org.cellang.viewsframework.view.table.TableDataView;

public class MyFavoritesCorpListView extends TableDataView<Row> implements HasDelegates, Refreshable {
	public static class Row {
		GroupCorpEntity groupCorp;
		CorpInfoEntity corpInfo;

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
	
	public static class CorpNameColumn extends AbstractColumn<Row> {

		public CorpNameColumn(AbstractTableDataProvider<Row> model) {
			super(model, "CorpName");
		}

		@Override
		public Object getValue(int rowIdx, Row rowObj) {
			return rowObj.corpInfo.getName();
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
			this.columnList.add(new CorpNameColumn(this));
			
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
				Row r = new Row(e);
				r.corpInfo = oc.getEntityService().getEntity(CorpInfoEntity.class, e.getCorpId());
				
				this.list.add(r);
			}
			this.fireTableDataChanged();
		}

	}

	public static final String GROUP_ID = "my-favorites";

	private String selectedCorpId;

	public MyFavoritesCorpListView(OperationContext oc) {
		super("MyFavorites", oc, new DataProvider(oc));
		MenuBar mbar = oc.getMenuBar();
		OpenBalanceSheetAction a2 = mbar.getMenuItemAction(OpenBalanceSheetAction.class);
		a2.setCorpListView(this);//
		
		OpenIncomeStatementAction a3 = mbar.getMenuItemAction(OpenIncomeStatementAction.class);
		a3.setCorpListView(this);//
		
		OpenCashFlowStatementAction a4 = mbar.getMenuItemAction(OpenCashFlowStatementAction.class);
		a4.setCorpListView(this);//

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

	public String getSelectedCorpId(){
		return this.selectedCorpId;
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
