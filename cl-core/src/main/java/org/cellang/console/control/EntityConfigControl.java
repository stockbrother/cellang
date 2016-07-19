package org.cellang.console.control;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.view.ExtendingProperty;
import org.cellang.console.view.HasDelagates;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see EntityConfig
 * @author wu
 *
 */
public abstract class EntityConfigControl<E extends EntityObject> implements HasDelagates {
	private static final Logger LOG = LoggerFactory.getLogger(EntityConfigControl.class);

	// default sorter.
	private static Comparator<Method> DEF = new Comparator<Method>() {

		@Override
		public int compare(Method o1, Method o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	Comparator<Method> comparator = DEF;

	Map<String, ExtendingProperty> extendingPropertyMap = new HashMap<>();

	@Override
	public <T> T getDelegate(Class<T> cls) {

		return null;
	}

	protected void addExtendingProperty(ExtendingProperty ep, boolean install) {
		this.extendingPropertyMap.put(ep.getName(), ep);
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

	public List<ExtendingProperty> getExtendingPropertyList() {
		return new ArrayList<>(this.extendingPropertyMap.values());
	}

	public ExtendingProperty getExtendingProperty(String key) {
		//
		return this.extendingPropertyMap.get(key);
	}

}
