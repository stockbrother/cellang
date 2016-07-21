package org.cellang.console.control;

import java.util.ArrayList;
import java.util.List;

import org.cellang.console.ext.CorpEPExtendingProperty;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.InterestedCorpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorpInfoEntityConfigControl extends EntityConfigControl<CorpInfoEntity>implements HasActions {

	private static final Logger LOG = LoggerFactory.getLogger(CorpInfoEntityConfigControl.class);
	private List<Action> actions = new ArrayList<>();
	EntitySessionFactory entitySessions;

	public CorpInfoEntityConfigControl(EntitySessionFactory entitySessions) {
		this.entitySessions = entitySessions;
		actions.add(new Action() {

			@Override
			public String getName() {
				return "Add to Interested";
			}

			@Override
			public void perform() {
				CorpInfoEntityConfigControl.this.addToInterested();
			}
		});
		this.addExtendingProperty(new CorpEPExtendingProperty(1), true);
		this.addExtendingProperty(new CorpEPExtendingProperty(5), true);

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

	protected void addToInterested() {
		CorpInfoEntity ce = this.selected;
		if (ce == null) {
			LOG.debug("no selected entity");
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("add corp:" + ce.getCode() + " as interested.");
		}
		this.entitySessions.execute(new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				InterestedCorpEntity ic = new InterestedCorpEntity();
				ic.setId(ce.getId());//
				ic.setCorpId(ce.getId());//
				es.save(ic);
				return null;
			}
		});
	}

}
