package org.cellang.console.control;

import java.util.ArrayList;
import java.util.List;

import org.cellang.console.ext.CorpEPExtendingProperty;
import org.cellang.console.model.EntityObjectTableDataProvider;
import org.cellang.console.ops.OperationContext;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.FavoriteActionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see EntityObjectTableDataProvider for creating of favorite entity.
 * @author wu
 *
 */
public class FavoriteActionEntityConfigControl extends EntityConfigControl<FavoriteActionEntity>implements HasActions {

	private static final Logger LOG = LoggerFactory.getLogger(FavoriteActionEntityConfigControl.class);
	private List<Action> actions = new ArrayList<>();
	EntitySessionFactory entitySessions;
	OperationContext oc;
	FavoriteActionFactory faf;

	public FavoriteActionEntityConfigControl(OperationContext oc, EntitySessionFactory entitySessions) {
		this.oc = oc;
		this.entitySessions = entitySessions;
		actions.add(new Action() {

			@Override
			public String getName() {
				return "Open Favorite";
			}

			@Override
			public void perform() {
				FavoriteActionEntityConfigControl.this.executeFavoriteAction();
			}
		});
		this.addExtendingProperty(new CorpEPExtendingProperty(1), true);
		this.addExtendingProperty(new CorpEPExtendingProperty(5), true);
		this.faf = new FavoriteActionFactory(this.oc);
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (HasActions.class.equals(cls)) {
			return (T) this;
		} else if (SelectionListener.class.equals(cls)) {
			return (T) this;
		} else if (EntitySessionFactory.class.equals(cls)) {
			return (T) this.entitySessions;
		}
		return null;
	}

	@Override
	public List<Action> getActions(List<Action> al) {
		al.addAll(this.actions);
		return al;
	}

	protected void executeFavoriteAction() {
		FavoriteActionEntity ae = this.selected;
		if (ae == null) {
			LOG.debug("no selected entity");
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("execute action:" + ae.getName() + "");
		}
		this.faf.execute(ae);
	}

}
