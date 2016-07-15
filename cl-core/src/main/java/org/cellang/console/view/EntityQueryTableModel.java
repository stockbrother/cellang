package org.cellang.console.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityQueryTableModel extends AbstractTableModel {
	static final Logger LOG = LoggerFactory.getLogger(EntityQueryTableModel.class);

	int pageSize;
	List<? extends EntityObject> list;
	EntityConfig cfg;
	List<Method> getMethodList;

	public EntityQueryTableModel(EntityConfig cfg, int pageSize) {
		this.cfg = cfg;
		this.pageSize = pageSize;
		this.getMethodList = this.cfg.getGetMethodList();
	}

	@Override
	public int getRowCount() {

		return this.pageSize;

	}

	@Override
	public int getColumnCount() {
		return this.getMethodList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (this.list == null) {
			return null;
		}
		if (rowIndex >= this.list.size()) {
			return null;
		}
		EntityObject ec = this.list.get(rowIndex);
		Method getM = this.getMethodList.get(columnIndex);
		Object rt;
		try {
			rt = getM.invoke(ec);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		if (LOG.isTraceEnabled()) {
			LOG.trace("getValueAt,method:" + getM.getName() + ",rt:" + rt);//
		}
		return rt;
	}

}