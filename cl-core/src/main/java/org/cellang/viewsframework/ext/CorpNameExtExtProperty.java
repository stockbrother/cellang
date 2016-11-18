package org.cellang.viewsframework.ext;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.ExtendingPropertyEntity;
import org.cellang.viewsframework.HasDelegates;

public class CorpNameExtExtProperty extends AbstractExtendingPropertyDefine<ExtendingPropertyEntity, String> {

	public CorpNameExtExtProperty() {
		super(ExtendingPropertyEntity.class, String.class);
	}

	static class GetCorpNameOp extends EntityOp<String> {

		private String corpId;

		public GetCorpNameOp set(String corpId) {
			this.corpId = corpId;
			return this;
		}

		@Override
		public String execute(EntitySession es) {
			CorpInfoEntity rt = es.getSingle(CorpInfoEntity.class, "id", corpId);
			if (rt == null) {
				return null;
			}
			return rt.getName();
		}

	}

	EntitySessionFactory esf;
	GetCorpNameOp getOp = new GetCorpNameOp();

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "Corp Name";
	}

	@Override
	public boolean install(Object context) {
		if (context instanceof EntitySessionFactory) {
			this.esf = (EntitySessionFactory) context;
			return true;
		}
		if (context instanceof HasDelegates) {
			HasDelegates dela = (HasDelegates) context;
			EntitySessionFactory esf = dela.getDelegate(EntitySessionFactory.class);
			if (esf == null) {
				return false;
			}
			this.esf = esf;
			return true;
		}
		return false;
	}

	@Override
	public Object calculate(EntityObject eo) {
		ExtendingPropertyEntity ep = (ExtendingPropertyEntity) eo;
		ep.getEntityType();
		String id = ep.getEntityId();
		String name = this.esf.execute(getOp.set(id));
		return name;
	}

}
