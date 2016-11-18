package org.cellang.viewsframework.ext;

import java.math.BigDecimal;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.viewsframework.HasDelegates;

public abstract class AbstractCorpExtendingPropertyDefine  extends AbstractExtendingPropertyDefine<CorpInfoEntity, BigDecimal> {
	EntitySessionFactory esf;
	String key;
	public AbstractCorpExtendingPropertyDefine(String key) {
		super(CorpInfoEntity.class,BigDecimal.class);
		this.key = key;
	}

	@Override
	public String getKey() {
		// 
		return key;
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
	
}
