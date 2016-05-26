/**
 * Jun 10, 2012
 */
package org.cellang.core.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.commons.ObjectUtil;

/**
 * @author wu
 * 
 */
public class MapProperties<T> extends PropertiesSupport<T> {

	protected Map<String, T> map;

	public MapProperties() {
		this(new HashMap<String, T>());
	}

	public MapProperties(Map<String, T> map) {
		this.map = new HashMap<String, T>();
		this.map.putAll(map);

	}

	public static <T> MapProperties<T> valueOf(Map<String, T> map) {
		return new MapProperties<T>(map);
	}

	public static <T> MapProperties<T> valueOf(Object... keyValues) {
		MapProperties<T> rt = new MapProperties<T>();
		rt.setPropertiesByArray(keyValues);
		return rt;
	}

	public static <T> MapProperties<T> valueOf(HasProperties<T> pts) {

		return new MapProperties<T>(pts.getAsMap());

	}

	/* */
	@Override
	public void setProperty(String key, T value) {
		this.map.put(key, value);
	}

	@Override
	public T getProperty(String key) {
		return this.map.get(key);
	}

	public <X> X getProperty(Class<X> cls, String key) {
		return (X) this.getProperty(key);
	}

	/* */
	@Override
	public List<String> keyList() {
		List<String> rt = new ArrayList<String>();
		rt.addAll(this.map.keySet());
		return rt;

	}

	public <X> List<X> getPropertyList(Class<X> cls) {
		List<X> rt = new ArrayList<X>();
		for (Map.Entry<String, T> e : this.map.entrySet()) {
			Object o = e.getValue();
			if (cls.isInstance(o)) {
				rt.add(cls.cast(o));
			}
		}
		return rt;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof HasProperties)) {
			return false;
		}
		HasProperties pts = (HasProperties) obj;
		List<String> kl1 = this.keyList();
		List<String> kl2 = pts.keyList();
		if (kl1.size() != kl2.size()) {
			return false;

		}
		for (String key : kl1) {
			Object v1 = this.getProperty(key);
			Object v2 = pts.getProperty(key);
			if (!ObjectUtil.isNullSafeEquals(v1, v2)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return this.map.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.value.PropertiesI#removeProperty(java.lang.String)
	 */
	@Override
	public T removeProperty(String key) {
		return this.map.remove(key);

	}

}
