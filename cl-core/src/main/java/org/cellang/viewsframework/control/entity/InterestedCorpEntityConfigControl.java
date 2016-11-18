package org.cellang.viewsframework.control.entity;

import java.util.List;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.InterestedCorpEntity;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.control.SelectionListener;
import org.cellang.viewsframework.ext.InterestedCorpCorpNameExtendingProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterestedCorpEntityConfigControl extends EntityConfigControl<InterestedCorpEntity>implements HasActions {

	private static final Logger LOG = LoggerFactory.getLogger(InterestedCorpEntityConfigControl.class);
	EntitySessionFactory entitySessions;

	public InterestedCorpEntityConfigControl(EntitySessionFactory entitySessions) {
		this.entitySessions = entitySessions;
		
		this.addExtendingProperty(new InterestedCorpCorpNameExtendingProperty(), true);
	
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
	public List<Action> getActions(Object context, List<Action> al) {
		if(!(context instanceof InterestedCorpEntity)){
			return al;
		}
		al.add(new Action() {

			@Override
			public String getName() {
				return "Remove Interested";
			}

			@Override
			public void perform() {
				InterestedCorpEntityConfigControl.this.remove((InterestedCorpEntity)context);
			}
		});
		return al;
	}

	protected void remove(InterestedCorpEntity ce) {
	
		this.entitySessions.execute(new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				es.delete(InterestedCorpEntity.class,ce.getId());
				return null;
			}
		});
	}

}
