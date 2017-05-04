package org.cellang.viewsframework.table;

import java.util.List;

import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.viewsframework.HasDelegates;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.control.entity.EntityConfigControl;
import org.cellang.viewsframework.ops.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying entity list result.
 * 
 * @author wu
 *
 */
public class EntityObjectTableView extends TableDataView<EntityObject> implements HasActions {

	public static class EntityObjectSelectionEvent {
		public EntityObject entityObject;

		public EntityObjectSelectionEvent(EntityObject rowObj) {
			this.entityObject = rowObj;
		}

	}

	static final Logger LOG = LoggerFactory.getLogger(EntityObjectTableView.class);
	EntityConfigControl<?> ecc;

	OperationContext oc;

	public EntityObjectTableView(OperationContext oc, EntityConfig cfg, EntityConfigControl<?> ecc,
			List<String> extPropL, EntitySessionFactory es, int pageSize) {
		super("Entity of " + cfg.getTableName(), oc,
				new EntityObjectTableDataProvider(es, cfg, ecc, extPropL, pageSize));
		this.ecc = ecc;
		this.oc = oc;
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		if (this.ecc == null) {
			return al;
		}
		if (this.ecc instanceof HasDelegates) {
			HasActions has = this.ecc.getDelegate(HasActions.class);
			if (has != null) {
				has.getActions(context, al);
			}
		}
		return al;
	}

	@Override
	protected void onColumnSelected(Integer col, ColumnDefine colDef) {
		super.onColumnSelected(col, colDef);
		// this.helpers.columnSelected(col, colDef);
	}

	@Override
	protected void onRowSelected(Integer row, EntityObject rowObj) {
		super.onRowSelected(row, rowObj);//
		// this.helpers.rowSelected(rowObj);//
		oc.getEventBus().dispatch(new EntityObjectSelectionEvent(rowObj));
	}

}
