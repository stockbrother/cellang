package org.cellang.console.ext;

import org.cellang.console.view.HasDelagates;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.ExtendingPropertyEntity;

public class CorpNameExtExtProperty implements ExtendingPropertyDefine<ExtendingPropertyEntity> {

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
	public Class<ExtendingPropertyEntity> getEntityClass() {

		return ExtendingPropertyEntity.class;
	}

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
		if (context instanceof HasDelagates) {
			HasDelagates dela = (HasDelagates) context;
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
	public Object getValue(EntityObject eo) {
		ExtendingPropertyEntity ep = (ExtendingPropertyEntity) eo;
		ep.getEntityType();
		String id = ep.getEntityId();
		String name = this.esf.execute(getOp.set(id));
		return name;
	}

}
