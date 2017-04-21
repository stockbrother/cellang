package org.cellang.viewsframework.corpgrouping;

import java.util.ArrayList;
import java.util.List;

import org.cellang.core.entity.CorpGroupEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityQuery;
import org.cellang.corpsviewer.MenuBar;
import org.cellang.corpsviewer.myfavorites.OpenBalanceSheetAction;
import org.cellang.viewsframework.HasDelegates;
import org.cellang.viewsframework.control.Refreshable;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.table.AbstractColumn;
import org.cellang.viewsframework.view.table.AbstractTableDataProvider;
import org.cellang.viewsframework.view.table.LineNumberColumn;
import org.cellang.viewsframework.view.table.TableDataView;

public class CorpGroupListView extends TableDataView<CorpGroupRowData> implements HasDelegates, Refreshable {

	public static class GroupIdColumn extends AbstractColumn<CorpGroupRowData> {

		public GroupIdColumn(AbstractTableDataProvider<CorpGroupRowData> model) {
			super(model, "GroupId");
		}

		@Override
		public Object getValue(int rowIdx, CorpGroupRowData rowObj) {
			return rowObj.corpGroup.getGroupId();
		}

	}

	public static class GroupTypeColumn extends AbstractColumn<CorpGroupRowData> {

		public GroupTypeColumn(AbstractTableDataProvider<CorpGroupRowData> model) {
			super(model, "GroupType");
		}

		@Override
		public Object getValue(int rowIdx, CorpGroupRowData rowObj) {
			return rowObj.corpGroup.getGroupType();
		}

	}

	public static class GroupDateColumn extends AbstractColumn<CorpGroupRowData> {

		public GroupDateColumn(AbstractTableDataProvider<CorpGroupRowData> model) {
			super(model, "GroupDate");
		}

		@Override
		public Object getValue(int rowIdx, CorpGroupRowData rowObj) {
			return rowObj.corpGroup.getGroupDate();
		}

	}

	public static class DataProvider extends AbstractTableDataProvider<CorpGroupRowData> implements Refreshable {
		OperationContext oc;
		List<CorpGroupRowData> list = new ArrayList<CorpGroupRowData>();

		public DataProvider(OperationContext oc) {
			this.oc = oc;
			this.columnList.add(new LineNumberColumn<CorpGroupRowData>(this));
			this.columnList.add(new GroupIdColumn(this));
			this.columnList.add(new GroupTypeColumn(this));
			this.columnList.add(new GroupDateColumn(this));
		}

		@Override
		public int getRowCount() {
			return this.list.size();
		}

		@Override
		public CorpGroupRowData getRowObject(int idx) {
			return this.list.get(idx);//
		}

		@Override
		public void refresh() {
			this.list.clear();
			EntityConfig cfg = oc.getEntityConfigFactory().get(CorpGroupEntity.class);
			List<CorpGroupEntity> el = new EntityQuery<CorpGroupEntity>(cfg).orderBy("groupDate")
					.execute(oc.getEntityService());
			for (CorpGroupEntity e : el) {
				this.list.add(new CorpGroupRowData(e));
			}
			this.fireTableDataChanged();
		}

	}

	CorpGroupRowData selected;
	DeleteCorpGroupAction action;

	public CorpGroupListView(OperationContext oc) {
		super("CorpGroupList", oc, new DataProvider(oc));
		MenuBar mbar = oc.getMenuBar();

		// TODO
		DeleteCorpGroupAction action = mbar.getMenuItemAction(DeleteCorpGroupAction.class);
		action.setView(this);
		

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
	public void refresh() {
		DataProvider dp = (DataProvider) this.dp;
		dp.refresh();
	}

	@Override
	protected void onRowSelected(Integer row, CorpGroupRowData rowObj) {
		this.selected = rowObj;

	}

	public CorpGroupRowData getSelectedCorpGroup() {
		return this.selected;
	}

}
