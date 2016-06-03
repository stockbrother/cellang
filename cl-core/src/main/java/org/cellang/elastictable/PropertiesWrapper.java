/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elastictable;

import org.cellang.commons.ObjectUtil;
import org.cellang.core.lang.HasProperties;
import org.cellang.core.lang.MapProperties;

/**
 * @author wu
 * 
 */
public class PropertiesWrapper<O, T extends HasProperties<O>> {

	protected T target;

	public <X extends PropertiesWrapper<O, T>> X attachTo(T t) {
		this.target = t;
		return (X) this;
	}

	public PropertiesWrapper<O, T> applyFrom(PropertiesWrapper<O, T>... pwss) {
		for (PropertiesWrapper<O, T> pws : pwss) {
			this.applyFrom(pws.target);
		}
		return this;
	}

	public PropertiesWrapper<O, T> applyFrom(T... ptss) {

		for (T pts : ptss) {
			this.target.setProperties(pts);//
		}

		return this;
	}

	public HasProperties<O> convert() {
		//
		HasProperties<O> rt = new MapProperties<O>();
		rt.setProperties(this.target);
		return rt;
	}

	public HasProperties<O> convert(String[] from, boolean[] force, String[] to) {
		//
		HasProperties<O> rt = new MapProperties<O>();
		for (int i = 0; i < from.length; i++) {
			O value = this.getProperty(from[i], force[i]);
			rt.setProperty(to[i], value);
		}
		return rt;
	}

	public O getProperty(String key, boolean force) {
		return this.target.getProperty(key, force);
	}

	public void setProperty(String key, O value) {
		this.target.setProperty(key, value);
	}

	/**
	 * @return the target
	 */
	public T getTarget() {
		return target;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !this.getClass().equals(obj.getClass())) {
			return false;
		}
		RowObject nw = (RowObject) obj;
		return ObjectUtil.nullSafeEquals(this.target, nw.target);
	}

	@Override
	public String toString() {
		return "wrapper of:" + this.target;
	}
}
