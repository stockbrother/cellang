package org.cellang.console.control;

import java.util.ArrayList;
import java.util.List;

import org.cellang.console.view.View;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.InterestedCorpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorpInfoEntityConfigControl extends EntityConfigControl<CorpInfoEntity>
		implements HasActions, EntityObjectSelectionListener {

	private static final Logger LOG = LoggerFactory.getLogger(CorpInfoEntityConfigControl.class);
	private List<Action> actions = new ArrayList<>();
	EntitySessionFactory entitySessions;
	private CorpInfoEntity selected;

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
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (HasActions.class.equals(cls)) {
			return (T) this;
		} else if (EntityObjectSelectionListener.class.equals(cls)) {
			return (T) this;
		}
		return null;
	}

	@Override
	public List<Action> getActions(View view, List<Action> al) {
		al.addAll(this.actions);
		return al;
	}

	protected void addToInterested() {
		if (this.selected == null) {
			LOG.debug("no selected entity");
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("add corp:" + this.selected.getCode() + " as interested.");
		}
		this.entitySessions.execute(new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				InterestedCorpEntity ic = new InterestedCorpEntity();
				ic.setId(selected.getId());//
				ic.setCorpId(selected.getId());//
				es.save(ic);
				return null;
			}
		});
	}

	@Override
	public void onEntitySelected(EntityObject eo) {
		this.selected = (CorpInfoEntity) eo;
	}
}
