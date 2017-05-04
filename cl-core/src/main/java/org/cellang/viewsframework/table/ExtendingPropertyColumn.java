package org.cellang.viewsframework.table;

import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.ExtendingPropertyEntity;
import org.cellang.viewsframework.ext.ExtendingPropertyDefine;
import org.cellang.viewsframework.ext.ExtendingPropertyGetOp;

class ExtendingPropertyColumn extends AbstractColumn<EntityObject> {
	ExtendingPropertyDefine def;
	EntitySessionFactory esf;
	ExtendingPropertyGetOp epGetOp = new ExtendingPropertyGetOp();

	ExtendingPropertyColumn(EntitySessionFactory esf, EntityObjectTableDataProvider model,
			ExtendingPropertyDefine calculator) {
		super(model, calculator.getKey());
		this.esf = esf;
		this.def = calculator;
	}

	@Override
	public Object getValue(int rowIndex,EntityObject ec) {

		if (ec == null) {
			return null;
		}
		ExtendingPropertyEntity ep = this.esf.execute(this.epGetOp.set(def.getEntityClass(), ec.getId(), def.getKey()));
		if (ep == null) {
			return null;
		}
		return ep.getValue();
	}

	@Override
	public String getFilterableColumn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getValueRenderingClass() {
		return def.getValueClass();
	}

}