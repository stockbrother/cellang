package org.cellang.console.model;

import java.util.List;

import org.cellang.console.control.HasActions;
import org.cellang.core.entity.EntityConfig;

public class EntityConfigTableDataProvider extends AbstractTableDataProvider<EntityConfig>  {
	List<EntityConfig> list;

	public EntityConfigTableDataProvider(List<EntityConfig> list) {
		this.list = list;
	}

	@Override
	public int getRowCount() {

		return this.list.size();

	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object rt = null;
		EntityConfig ec = this.list.get(rowIndex);

		switch (columnIndex) {
		case 0:
			rt = String.valueOf(rowIndex);
			break;
		case 1:
			rt = ec == null ? null : ec.getEntityClass();
			break;

		case 2:
			rt = ec == null ? null : ec.getTableName();
			break;

		}
		return rt;
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (cls.equals(HasActions.class)) {

		}
		return null;
	}

	@Override
	public EntityConfig getRowObject(int idx) {
		if (idx >= this.list.size()) {
			return null;
		}
		return this.list.get(idx);
	}

}
