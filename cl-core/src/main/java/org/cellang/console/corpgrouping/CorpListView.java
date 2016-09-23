package org.cellang.console.corpgrouping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.control.Refreshable;
import org.cellang.console.control.entity.EntityConfigControl;
import org.cellang.console.ext.ExtendingPropertyDefine;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.table.AbstractColumn;
import org.cellang.console.view.table.AbstractTableDataProvider;
import org.cellang.console.view.table.ColumnDefine;
import org.cellang.console.view.table.EntityObjectTableDataProvider;
import org.cellang.console.view.table.LineNumberColumn;
import org.cellang.console.view.table.TableDataView;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying entity list result.
 * 
 * @author wu
 *
 */
public class CorpListView extends TableDataView<CorpInfoEntity> {

	public static class CorpIdColumn extends AbstractColumn<CorpInfoEntity> {

		public CorpIdColumn(AbstractTableDataProvider<CorpInfoEntity> model) {
			super(model, "CorpId");
		}

		@Override
		public Object getValue(int rowIdx, CorpInfoEntity rowObj) {

			return rowObj.getId();
		}

	}

	public static class CorpNameColumn extends AbstractColumn<CorpInfoEntity> {

		public CorpNameColumn(AbstractTableDataProvider<CorpInfoEntity> model) {
			super(model, "CorpId");
		}

		@Override
		public Object getValue(int rowIdx, CorpInfoEntity rowObj) {
			return rowObj.getName();
		}
	}

	public static class DataProvider extends AbstractTableDataProvider<CorpInfoEntity> implements Refreshable {
		private static final Logger LOG = LoggerFactory.getLogger(DataProvider.class);

		List<CorpInfoEntity> list;
		private Map<String, String> likeMap = new HashMap<String, String>();

		OperationContext oc;

		public DataProvider(OperationContext oc) {
			this.oc = oc;
			this.columnList.add(new LineNumberColumn<CorpInfoEntity>(this));
			this.columnList.add(new CorpIdColumn(this));
			this.columnList.add(new CorpNameColumn(this));

			this.refresh();
		}

		@Override
		public int getRowCount() {
			return this.list.size();

		}

		@Override
		public int getColumnCount() {
			return this.columnList.size();
		}

		public Map<String, String> getLikeMap() {
			return likeMap;
		}

		@Override
		public void refresh() {
			EntityConfig cfg = oc.getEntityConfigFactory().get(CorpInfoEntity.class);

			List<CorpInfoEntity> el = new EntityQuery<CorpInfoEntity>(cfg).like(this.getLikeMap()).orderBy("code")
					.execute(oc.getEntityService());

			this.list = el;
			this.fireTableDataChanged();
		}

		public EntityObject getEntityObject(int idx) {
			//
			if (idx < 0 || idx >= this.list.size()) {
				return null;
			}
			return this.list.get(idx);//
		}

		@Override
		public CorpInfoEntity getRowObject(int idx) {
			return this.list.get(idx);
		}

	}

	static final Logger LOG = LoggerFactory.getLogger(CorpListView.class);

	OperationContext oc;
	CorpInfoEntity selected;

	public CorpListView(OperationContext oc) {
		super("Corp Search", oc, new DataProvider(oc));
		// TODO notify by event?
		AddToMyFavoritesAction a = oc.getMenuBar().getMenuItemAction(AddToMyFavoritesAction.class);
		a.setListView(this);//
		this.oc = oc;
	}

	@Override
	protected void onColumnSelected(Integer col, ColumnDefine<CorpInfoEntity> colDef) {
		super.onColumnSelected(col, colDef);
	}

	@Override
	protected void onRowSelected(Integer row, CorpInfoEntity rowObj) {
		super.onRowSelected(row, rowObj);//
		this.selected = rowObj;
	}

	public CorpInfoEntity getSelected() {
		return this.selected;
	}

}
