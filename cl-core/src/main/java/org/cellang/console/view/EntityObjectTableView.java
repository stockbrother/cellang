package org.cellang.console.view;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.EntityConfigControl;
import org.cellang.console.control.HasActions;
import org.cellang.console.model.EntityObjectTableDataProvider;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * View for displaying entity list result.
 * 
 * @author wu
 *
 */
public class EntityObjectTableView extends TableDataView<EntityObject>implements HasActions {

	static final Logger LOG = LoggerFactory.getLogger(EntityObjectTableView.class);
	EntityConfigControl<?> ecc;

	public EntityObjectTableView(EntityConfig cfg, EntityConfigControl<?> ecc, EntitySessionFactory es, int pageSize) {
		super("Entities", new EntityObjectTableDataProvider(es, cfg, ecc, pageSize));
		this.ecc = ecc;
	}

	@Override
	public List<Action> getActions(View view, List<Action> al) {
		if (this.ecc == null) {
			return al;
		}
		HasActions has = this.ecc.getDelegate(HasActions.class);
		if (has != null) {
			return has.getActions(view, al);
		}
		return al;
	}
}
