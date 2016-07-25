package org.cellang.console.view.table;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.cellang.commons.util.BeanUtil;
import org.cellang.core.entity.EntityObject;

class GetterMethodColumn extends AbstractColumn<EntityObject> {
	Method method;

	public GetterMethodColumn(Method m, EntityObjectTableDataProvider model) {
		super(model, null);
		name = BeanUtil.getPropertyNameFromGetMethod(m);
		this.method = m;
	}

	@Override
	public Object getValue(int rowIndex,EntityObject ec) {
		
		if(ec == null){
			return null;
		}
		Object rt;
		try {
			rt = method.invoke(ec);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return rt;

	}

	@Override
	public String getFilterableColumn() {
		Class cls = method.getReturnType();
		if (String.class.equals(cls)) {
			return this.name;
		}
		return null;
	}

	@Override
	public Class<?> getValueRenderingClass() {
		return method.getReturnType();
	}
}