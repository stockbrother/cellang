package org.cellang.console.corpgrouping;

import java.util.ArrayList;
import java.util.List;

import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.table.AbstractColumn;
import org.cellang.console.view.table.AbstractTableDataProvider;
import org.cellang.console.view.table.LineNumberColumn;
import org.cellang.console.view.table.TableDataView;
import org.cellang.core.entity.CorpGroupEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityQuery;

public class CorpGroupListView extends TableDataView<CorpGroupRowData> {
	
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

	public static class DataProvider extends AbstractTableDataProvider<CorpGroupRowData> {
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

	public CorpGroupListView(OperationContext oc) {
		super("CorpGroupList", new DataProvider(oc));
		DataProvider dp = (DataProvider) this.dp;
		dp.refresh();
	}

}
