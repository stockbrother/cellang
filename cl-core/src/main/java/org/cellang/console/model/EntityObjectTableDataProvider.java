package org.cellang.console.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.util.BeanUtil;
import org.cellang.console.control.ColumnAppendable;
import org.cellang.console.control.ColumnOrderable;
import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.EntityConfigControl;
import org.cellang.console.control.Filterable;
import org.cellang.console.ext.ExtendingPropertyDefine;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityObjectTableDataProvider extends AbstractTableDataProvider<EntityObject>
		implements Filterable, DataPageQuerable, ColumnAppendable, DataChangable, ColumnChangable, ColumnOrderable {
	public static abstract class Column {
		EntityObjectTableDataProvider model;
		String name;

		Column(EntityObjectTableDataProvider model, String name) {
			this.model = model;
			this.name = name;
		}

		public abstract Object getValue(int rowIndex);

		// if the column is filterable, action ui will add conditional input
		// argument for filtering of the data table based on this column.
		public abstract String getFilterableColumn();

		public String getDisplayName() {

			return name;

		}
	}

	private static class LineNumberColumn extends Column {

		LineNumberColumn(EntityObjectTableDataProvider model) {
			super(model, "LN.");
		}

		@Override
		public Object getValue(int rowIndex) {
			return model.pageSize * model.pageNumber + rowIndex;
		}

		@Override
		public String getFilterableColumn() {
			return null;
		}

	}

	private static class ExtendingColumn extends Column {
		ExtendingPropertyDefine calculator;

		ExtendingColumn(EntityObjectTableDataProvider model, ExtendingPropertyDefine calculator) {
			super(model, calculator.getKey());
			this.calculator = calculator;
		}

		@Override
		public Object getValue(int rowIndex) {
			if (model.list == null || rowIndex > model.list.size() - 1) {
				return null;
			}
			EntityObject ec = model.list.get(rowIndex);
			return this.calculator.getValue(ec);
		}

		@Override
		public String getFilterableColumn() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	private static class GetterMethodColumn extends Column {
		Method method;

		public GetterMethodColumn(Method m, EntityObjectTableDataProvider model) {
			super(model, null);
			name = BeanUtil.getPropertyNameFromGetMethod(m);
			this.method = m;
		}

		@Override
		public Object getValue(int rowIndex) {
			if (model.list == null || rowIndex > model.list.size() - 1) {
				return null;
			}
			EntityObject ec = model.list.get(rowIndex);
			Object rt;
			try {
				rt = method.invoke(ec);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
			return rt;

		}

		@Override
		public String getFilterableColumn() {
			Class cls = method.getReturnType();
			if (String.class.equals(cls)) {
				return this.name;
			}
			return null;
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(EntityObjectTableDataProvider.class);

	protected int pageSize;

	protected int pageNumber = -1;

	private List<? extends EntityObject> list;
	EntityConfig cfg;
	List<Column> columnList = new ArrayList<>();

	private Map<String, String> likeMap = new HashMap<String, String>();
	EntitySessionFactory entityService;
	EntityConfigControl<?> ecc;

	List<DataChangedListener> dataChangedListenerList = new ArrayList<>();

	List<ColumnChangedListener> columnChangedListenerList = new ArrayList<>();

	String[] orderBy;

	public EntityObjectTableDataProvider(EntitySessionFactory entityService, EntityConfig cfg,
			EntityConfigControl<?> ecc, int pageSize) {
		this.ecc = ecc;
		this.cfg = cfg;
		this.entityService = entityService;
		this.pageSize = pageSize;
		Method[] getters = this.cfg.getGetMethodList().toArray(new Method[0]);
		Comparator<Method> columnSorter = ecc == null ? null : ecc.getColumnSorter();
		if (columnSorter != null) {
			Arrays.sort(getters, columnSorter);
		}

		for (Method m : getters) {
			Column col = new GetterMethodColumn(m, this);
			this.columnList.add(col);
		}

		this.columnList.add(0, new LineNumberColumn(this));
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
	public Object getValueAt(int rowIndex, int columnIndex) {

		Column col = this.columnList.get(columnIndex);
		Object rt = col.getValue(rowIndex);
		if (LOG.isTraceEnabled()) {
			LOG.trace("getValueAt,col:" + col + ",rt:" + rt);//
		}

		return rt;
	}

	@Override
	public String[] getFilterableColumnList() {
		List<String> rt = new ArrayList<String>();
		for (Column col : this.columnList) {
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
				.limit(this.pageSize).orderBy(this.orderBy).execute(this.entityService);

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

	@Override
	public String getColumnName(int column) {
		String cname = this.columnList.get(column).getDisplayName();
		String aname = super.getColumnName(column);
		return aname + "(" + cname + ")";
	}

	public EntityObject getEntityObject(int idx) {
		//
		if (idx < 0 || idx >= this.list.size()) {
			return null;
		}
		return this.list.get(idx);//
	}

	@Override
	public List<String> getExtenableColumnList() {

		List<String> rt = new ArrayList<>();
		if (this.ecc == null) {
			return rt;
		}
		List<ExtendingPropertyDefine> epL = this.ecc.getExtendingPropertyList();
		for (ExtendingPropertyDefine ep : epL) {
			rt.add(ep.getKey());
		}
		return rt;
	}

	@Override
	public void appendColumn(String columnName) {
		if (this.ecc == null) {
			throw new RuntimeException("not supported.");
		}
		ExtendingPropertyDefine cal = this.ecc.getExtendingProperty(columnName);
		ExtendingColumn ec = new ExtendingColumn(this, cal);
		this.columnList.add(ec);

		ExtendingPropertyUpdater epu = new ExtendingPropertyUpdater(cal, this.entityService);
		epu.execute();

		fireColumnChanged();
	}

	private void fireTableDataChanged() {
		for (DataChangedListener l : this.dataChangedListenerList) {
			l.onDataChanged();
		}
	}

	private void fireColumnChanged() {
		for (ColumnChangedListener l : this.columnChangedListenerList) {
			l.onColumnChanged();
		}
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (cls.equals(DataChangable.class)) {
			return (T) this;
		} else if (cls.equals(ColumnChangable.class)) {
			return (T) this;
		} else if (cls.equals(DataPageQuerable.class)) {
			return (T) this;
		} else if (cls.equals(Filterable.class)) {
			return (T) this;
		} else if (cls.equals(ColumnAppendable.class)) {
			return (T) this;
		} else if (cls.equals(ColumnOrderable.class)) {
			return (T) this;
		}

		return null;
	}

	@Override
	public void addDataChangeListener(DataChangedListener l) {
		this.dataChangedListenerList.add(l);
	}

	@Override
	public void addColumnListener(ColumnChangedListener l) {
		this.columnChangedListenerList.add(l);
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
		return this.cfg.getPropertyKeyList();
	}

	@Override
	public void setOrderBy(String key) {
		if (key == null) {
			this.orderBy = null;
		} else {
			this.orderBy = new String[] { key };
		}

		this.refresh();
	}
}
