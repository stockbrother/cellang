package org.cellang.viewsframework.table;

import java.util.ArrayList;
import java.util.List;

import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.ExtendingPropertyEntity;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.control.Refreshable;
import org.cellang.viewsframework.control.entity.EntityConfigControl;
import org.cellang.viewsframework.control.entity.EntityConfigManager;
import org.cellang.viewsframework.ext.ExtendingPropertyDefine;
import org.cellang.viewsframework.model.ColumnChangedListener;
import org.cellang.viewsframework.model.DataChangedListener;
import org.cellang.viewsframework.table.ExtendingPropertyMasterTableDataProvider.RowObject;

public class ExtendingPropertyMasterTableDataProvider extends AbstractTableDataProvider<RowObject>
		implements Refreshable {
	public static class RowObject {
		private EntityConfig entityConfig;
		public ExtendingPropertyDefine def;

		private RowObject(EntityConfig entityConfig, ExtendingPropertyDefine def) {
			this.entityConfig = entityConfig;
			this.def = def;
		}
	}

	public static abstract class RowObjectColumn extends AbstractColumn<RowObject> {
		ExtendingPropertyMasterTableDataProvider model;

		RowObjectColumn(ExtendingPropertyMasterTableDataProvider model, String name) {
			super(model, name);
			this.model = model;
		}

		@Override
		public String getFilterableColumn() {
			return null;
		}

		@Override
		public Object getValue(int rowIndex,RowObject ro) {
			if (rowIndex < 0 || rowIndex > this.model.list.size() - 1) {
				return null;
			}
			return getValue(ro);
		}

		protected abstract Object getValue(RowObject ro);

	}

	public static class TableNameRowObjectColumn extends RowObjectColumn {

		TableNameRowObjectColumn(ExtendingPropertyMasterTableDataProvider model, String name) {
			super(model, name);
		}

		@Override
		public Object getValue(RowObject ro) {
			return ro.entityConfig.getTableName();
		}

	}

	public static class ExtendingPropertyKeyRowObjectColumn extends RowObjectColumn {

		ExtendingPropertyKeyRowObjectColumn(ExtendingPropertyMasterTableDataProvider model, String name) {
			super(model, name);
		}

		@Override
		public Object getValue(RowObject ro) {
			return ro.def.getKey();
		}

	}

	private static class CounterRowOp extends EntityOp<Long> {
		private RowObject ro;

		public CounterRowOp set(RowObject ro) {
			this.ro = ro;
			return this;
		}

		@Override
		public Long execute(EntitySession es) {
			Long rt = es.counter(ExtendingPropertyEntity.class, new String[] { "entityType", "key" },
					new Object[] { ro.def.getEntityClass().getName(), ro.def.getKey() });

			return rt;
		}

	}

	public static class ExtendingPropertyCounterRowObjectColumn extends RowObjectColumn {
		private CounterRowOp op = new CounterRowOp();

		ExtendingPropertyCounterRowObjectColumn(ExtendingPropertyMasterTableDataProvider model, String name) {
			super(model, name);
		}

		@Override
		public Object getValue(RowObject ro) {

			return model.esf.execute(op.set(ro));
		}

	}

	List<RowObject> list = new ArrayList<>();
	EntitySessionFactory esf;

	public ExtendingPropertyMasterTableDataProvider(EntitySessionFactory esf, EntityConfigFactory ecf,
			EntityConfigManager ecm) {
//		this.addDelagate(Refreshable.class, this);
		this.esf = esf;
		this.columnList.add(new TableNameRowObjectColumn(this, "Table Name"));

		this.columnList.add(new ExtendingPropertyKeyRowObjectColumn(this, "Extending Property"));

		this.columnList.add(new ExtendingPropertyCounterRowObjectColumn(this, "Total Values"));

		for (EntityConfig ec : ecf.getEntityConfigList()) {
			EntityConfigControl ecc = ecm.getEntityConfigControl(ec);
			if (ecc == null) {
				continue;
			}
			List<ExtendingPropertyDefine> defL = ecc.getExtendingPropertyList();
			for (ExtendingPropertyDefine def : defL) {
				RowObject ro = new RowObject(ec, def);
				this.list.add(ro);
			}
		}

	}

	@Override
	public int getRowCount() {

		return this.list.size();

	}

	@Override
	public RowObject getRowObject(int idx) {
		if (idx >= this.list.size()) {
			return null;
		}
		return this.list.get(idx);
	}

	@Override
	public int getRowNumber(int rowIndex) {
		return rowIndex;
	}

	@Override
	public void refresh() {
		this.fireTableDataChanged();
	}


}
