package org.cellang.console.customized;

import java.math.BigDecimal;

import org.cellang.console.HasDelegates;
import org.cellang.core.entity.EntitySessionFactory;

public abstract class AbstractCustomizedReportItemDefine implements CustomizedReportItemDefine {
	String key;
	EntitySessionFactory esf;

	AbstractCustomizedReportItemDefine(String key) {
		this.key = key;
	}

	@Override
	public String getKey() {
		return this.key;
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
