package org.cellang.console.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.cellang.commons.util.BeanUtil;
import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.Filterable;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityQuery;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityQueryTableModel extends AbstractTableModel implements Filterable, DataPageQuerable {

	private static abstract class Column {
		EntityQueryTableModel model;

		Column(EntityQueryTableModel model) {
			this.model = model;
		}

		public abstract Object getValue(int rowIndex);

		public abstract String getFilterableColumn();
	}

	private static class LineNumberColumn extends Column {

		LineNumberColumn(EntityQueryTableModel model) {
			super(model);
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

	private static class GetterMethodColumn extends Column {
		Method method;

		public GetterMethodColumn(Method m, EntityQueryTableModel model) {
			super(model);
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
				// TODo other type of field.
				String rt = BeanUtil.getPropertyNameFromGetMethod(method);
				return rt;
			}
			return null;
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(EntityQueryTableModel.class);

	protected int pageSize;

	protected int pageNumber = -1;

	private List<? extends EntityObject> list;
	EntityConfig cfg;
	List<Column> columnList = new ArrayList<>();
	private Map<String, String> likeMap = new HashMap<String, String>();
	EntitySessionFactory entityService;
	Runnable dataUpdateListener;

	public EntityQueryTableModel(EntitySessionFactory entityService, EntityConfig cfg, int pageSize,
			Runnable dataUpdateListener) {
		this.dataUpdateListener = dataUpdateListener;
		this.cfg = cfg;
		this.entityService = entityService;
		this.pageSize = pageSize;
		this.columnList.add(new LineNumberColumn(this));
		for (Method m : this.cfg.getGetMethodList()) {
			Column col = new GetterMethodColumn(m, this);
			this.columnList.add(col);
		}
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
	public String[] getColumn() {
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
				.limit(this.pageSize).execute(this.entityService);

		this.list = el;
		this.dataUpdateListener.run();
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
}