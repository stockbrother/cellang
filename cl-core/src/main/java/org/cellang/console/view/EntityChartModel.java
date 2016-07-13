package org.cellang.console.view;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import org.cellang.console.chart.ChartModel;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityChartModel extends ChartModel {
	private static final Logger LOG = LoggerFactory.getLogger(EntityChartModel.class);

	int pageSize;
	private List<? extends EntityObject> list;
	EntityConfig cfg;
	List<Method> getMethodList;
	Method xGetMethod;
	Method yGetMethod;

	public EntityChartModel(EntityConfig cfg, Method xGetMethod, Method yGetMethod, int pageSize) {

		this.xGetMethod = xGetMethod;
		this.yGetMethod = yGetMethod;
		this.cfg = cfg;
		this.pageSize = pageSize;
		this.getMethodList = this.cfg.getGetMethodList();
	}

	public void setEntityObjectList(List<? extends EntityObject> list) {
		this.list = list;
		super.invalidCache();
	}

	public List<? extends EntityObject> getEntityObjectList() {
		return this.list;
	}

	@Override
	public int getCount() {
		return this.pageSize;
	}

	@Override
	public String getXValue(int idx) {
		if (this.list == null) {
			return null;
		}
		if (idx >= this.list.size()) {
			return null;
		}
		EntityObject eo = this.list.get(idx);
		try {
			Object value = this.xGetMethod.invoke(eo);
			return String.valueOf(value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOG.error("", e);//
			return "Error:" + e.getMessage();
		}

	}

	@Override
	public BigDecimal getYValue(int idx) {
		if (this.list == null) {
			return null;
		}
		if (idx >= this.list.size()) {
			return null;
		}
		EntityObject eo = this.list.get(idx);
		try {
			Object value = this.yGetMethod.invoke(eo);
			if (value instanceof BigDecimal) {
				BigDecimal rt = (BigDecimal) value;				
				return rt;
			} else {
				return null;
			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOG.error("", e);//
			return null;
		}
	}

}
