package org.cellang.viewsframework.table;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.viewsframework.control.ColumnOrderable;
import org.cellang.viewsframework.control.DataPageQuerable;
import org.cellang.viewsframework.control.Favoriteable;
import org.cellang.viewsframework.control.Filterable;
import org.cellang.viewsframework.control.Refreshable;
import org.cellang.viewsframework.control.entity.EntityConfigControl;
import org.cellang.viewsframework.control.entity.FavoriteActionFactory;
import org.cellang.viewsframework.ext.ExtendingPropertyDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EntityObject as the RowObject.
 * 
 * @see FavoriteActionFactory for open favorite action.
 * @author wu
 *
 */
public class EntityObjectTableDataProvider extends AbstractTableDataProvider<EntityObject>
		implements Filterable, DataPageQuerable, ColumnOrderable, Favoriteable, Refreshable {
	private static final Logger LOG = LoggerFactory.getLogger(EntityObjectTableDataProvider.class);

	protected int pageSize;

	protected int pageNumber = -1;

	List<? extends EntityObject> list;
	EntityConfig cfg;

	private Map<String, String> likeMap = new HashMap<String, String>();
	EntitySessionFactory entityService;
	EntityConfigControl<?> ecc;

	String[] orderBy;

	//String orderByExtendingPropertyKey;

	public EntityObjectTableDataProvider(EntitySessionFactory entityService, EntityConfig cfg,
			EntityConfigControl<?> ecc, List<String> extPropL, int pageSize) {

		this.ecc = ecc;
		this.cfg = cfg;
		this.entityService = entityService;
		this.pageSize = pageSize;
		Method[] getters = this.cfg.getGetMethodList().toArray(new Method[0]);
		Comparator<Method> columnSorter = ecc == null ? null : ecc.getColumnSorter();
		if (columnSorter != null) {
			Arrays.sort(getters, columnSorter);
		}

		this.columnList.add(new LineNumberColumn<EntityObject>(this));
		for (Method m : getters) {
			AbstractColumn<EntityObject> col = new GetterMethodColumn(m, this);
			this.columnList.add(col);
		}

		for (String col : extPropL) {
			this.appendColumn(col, false);//
		}

		this.nextPage();
	}

	@Override
	public int getRowCount() {

		return this.pageSize;

	}

	@Override
	public int getColumnCount() {
		return this.columnList.size();
	}

	@Override
	public String[] getFilterableColumnList() {
		List<String> rt = new ArrayList<String>();
		for (AbstractColumn<EntityObject> col : this.columnList) {
			String fname = col.getFilterableColumn();
			if (fname == null) {
				continue;
			}
			rt.add(fname);
		}
		return rt.toArray(new String[rt.size()]);

	}

	@Override
	public void setLike(String column, String value) {
		this.likeMap.put(column, value);
	}

	@Override
	public void unsetLike(String column) {
		this.likeMap.remove(column);
	}

	public Map<String, String> getLikeMap() {
		return likeMap;
	}

	@Override
	public void prePage() {
		if (this.pageNumber == 0) {
			return;
		}
		this.pageNumber--;
		this.query();
	}

	private void query() {
		int offset = this.pageNumber * this.pageSize;
		List<? extends EntityObject> el = new EntityQuery<>(cfg).like(this.getLikeMap()).offset(offset)
				.limit(this.pageSize).orderBy(this.orderBy)
				//.orderByExtendingPropertyKey(this.orderByExtendingPropertyKey)
				.execute(this.entityService);

		this.list = el;
		this.fireTableDataChanged();
	}

	@Override
	public void nextPage() {
		if (list != null && list.size() < this.pageSize) {
			return;
		}
		this.pageNumber++;
		this.query();

	}

	@Override
	public void refresh() {
		this.query();
	}

	public EntityObject getEntityObject(int idx) {
		//
		if (idx < 0 || idx >= this.list.size()) {
			return null;
		}
		return this.list.get(idx);//
	}
//
//	@Override
//	public List<String> getExtenableColumnList() {
//
//		List<String> rt = new ArrayList<>();
//		if (this.ecc == null) {
//			return rt;
//		}
//		List<ExtendingPropertyDefine> epL = this.ecc.getExtendingPropertyList();
//		for (ExtendingPropertyDefine ep : epL) {
//			rt.add(ep.getKey());
//		}
//		return rt;
//	}
//
//	@Override
//	public void appendColumn(String columnName) {
//		this.appendColumn(columnName, true);//
//	}

	private void appendColumn(String columnName, boolean save) {
		if (this.ecc == null) {
			throw new RuntimeException("not supported.");
		}
		ExtendingPropertyDefine cal = this.ecc.getExtendingProperty(columnName);
		cal.install(this.entityService);// TODO not install many times.
		ExtendingPropertyColumn ec = new ExtendingPropertyColumn(this.entityService, this, cal);
		this.columnList.add(ec);

		fireColumnChanged();
	}

	@Override
	public EntityObject getRowObject(int idx) {
		if (idx >= this.list.size()) {
			return null;
		}
		return this.list.get(idx);
	}

	@Override
	public List<String> getOrderableColumnList() {
		List<String> rt = new ArrayList<>();
		rt.addAll(this.cfg.getPropertyKeyList());
		//rt.addAll(this.getExtenableColumnList());

		return rt;
	}

	@Override
	public void setOrderBy(String key) {
		if (key == null) {
			this.orderBy = null;
		} else {
			if (this.cfg.containsGetPropertyKey(key)) {
				// the key is the column name of this table.
				this.orderBy = new String[] { key };
			} else {
				// the key is extending property key column value(for instance
				// P/E).
				//this.orderByExtendingPropertyKey = key;
			}
		}

		this.refresh();
	}

	@Override
	public String getFavoriteType() {
		return "open-view";
	}

	@Override
	public String getFavoriteContent() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.cfg.getEntityClass().getName())//
				.append(";")//
		;

		for (AbstractColumn<EntityObject> col : columnList) {
			if (col instanceof ExtendingPropertyColumn) {
				ExtendingPropertyColumn ec = (ExtendingPropertyColumn) col;
				sb.append(ec.def.getKey());//
				sb.append(",");
			}

		}
		;
		return sb.toString();
	}

	@Override
	public int getRowNumber(int rowIndex) {
		return this.pageSize * this.pageNumber + rowIndex + 1;
	}

}
