package org.cellang.console.control;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.cellang.console.view.HasDelagates;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
/**
 * @see EntityConfig 
 * @author wu
 *
 */
public abstract class EntityConfigControl<E extends EntityObject> implements HasDelagates {

	// default sorter.
	private static Comparator<Method> DEF = new Comparator<Method>() {

		@Override
		public int compare(Method o1, Method o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	Comparator<Method> comparator = DEF;

	@Override
	public <T> T getDelegate(Class<T> cls) {

		return null;
	}

	public Comparator<Method> getColumnSorter() {
		return this.comparator;
	}

}
