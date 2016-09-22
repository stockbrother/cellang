package org.cellang.console.control.entity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.HasDelegates;
import org.cellang.console.control.DefaultHasDelagates;
import org.cellang.console.control.HasActions;
import org.cellang.console.ext.ExtendingPropertyDefine;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see EntityConfig
 * @author wu
 *
 */
public abstract class EntityConfigControl<E extends EntityObject> implements HasDelegates {
	private static final Logger LOG = LoggerFactory.getLogger(EntityConfigControl.class);

	// default sorter.
	private static Comparator<Method> DEF = new Comparator<Method>() {

		@Override
		public int compare(Method o1, Method o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	Comparator<Method> comparator = DEF;

	Map<String, ExtendingPropertyDefine> extendingPropertyMap = new HashMap<>();

	protected DefaultHasDelagates delagatesHelper = new DefaultHasDelagates();

	E selected;

	public EntityConfigControl() {
		this.delagatesHelper.putIf(HasActions.class, this);
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		return this.delagatesHelper.getDelegate(cls);
	}

	protected void addExtendingProperty(ExtendingPropertyDefine ep, boolean install) {
		this.extendingPropertyMap.put(ep.getKey(), ep);
		if (install) {
			boolean succ = ep.install(this);
			if (!succ) {
				LOG.warn("install failed:" + ep);
			}
		}
	}
	
	public Comparator<Method> getColumnSorter() {
		return this.comparator;
	}

	public List<ExtendingPropertyDefine> getExtendingPropertyList() {
		return new ArrayList<>(this.extendingPropertyMap.values());
	}

	public ExtendingPropertyDefine getExtendingProperty(String key) {
		//
		return this.extendingPropertyMap.get(key);
	}

	public void setSelected(EntityObject co) {
		this.selected = (E) co;
	}

}
