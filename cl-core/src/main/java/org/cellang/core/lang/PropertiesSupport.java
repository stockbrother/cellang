/**
 * Jun 22, 2012
 */
package org.cellang.core.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.commons.ObjectUtil;

/**
 * @author wu
 * 
 */
public abstract class PropertiesSupport<T> implements HasProperties<T> {
	/* */
	@Override
	public void setPropertiesByArray(Object... keyValues) {
		int i = 0;
		while (i < keyValues.length) {
			String key = String.valueOf(keyValues[i++]);
			Object value = keyValues[i++];
			T t = (T) value;
			this.setProperty(key, t);
		}
	}

	@Override
	public boolean getPropertyAsBoolean(String key, boolean def) {
		T rt = this.getProperty(key);
		if (rt == null) {
			return def;
		}
		return Boolean.valueOf("" + rt);
	}

	@Override
	public HasProperties<T> mergeFrom(HasProperties<T> pts) {
		HasProperties<T> rt = this.clone();
		rt.setProperties(pts);
		return rt;
	}

	@Override
	public HasProperties<T> clone() {
		HasProperties<T> rt = MapProperties.valueOf(this);
		rt.setProperties(this);
		return rt;
	}

	/* */
	@Override
	public void setProperties(HasProperties<T> pts) {
		this.setProperties(pts.getAsMap());
	}

	/* */
	@Override
	public void setProperties(Map<String, T> map) {
		for (Map.Entry<String, T> e : map.entrySet()) {
			this.setProperty(e);//
		}
	}

	@Override
	public void setProperty(Map.Entry<String, T> entry) {
		this.setProperty(entry.getKey(), entry.getValue());
	}

	/* */
	@Override
	public Map<String, T> getAsMap() {

		Map<String, T> rt = new HashMap<String, T>();
		List<String> kL = this.keyList();
		for (String key : kL) {
			rt.put(key, this.getProperty(key));

		}
		return rt;

	}

	@Override
	public boolean equals(Object kvs) {
		if (kvs == null || !(kvs instanceof HasProperties)) {
			return false;
		}
		HasProperties pts = (HasProperties) kvs;
		List<String> kL = pts.keyList();
		List<String> kL2 = this.keyList();
		if (kL.size() != kL2.size()) {
			return false;
		}
		for (String k : kL) {
			Object o1 = this.getProperty(k);
			Object o2 = pts.getProperty(k);
			if (!ObjectUtil.isNullSafeEquals(o1, o2)) {
				return false;
			}
		}
		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.value.PropertiesI#getPropertyAsCsv(java.lang.String)
	 */
	@Override
	public List<String> getPropertyAsCsv(String key) {
		List<String> rt = new ArrayList<String>();
		String v = (String) this.getProperty(key);
		if (v == null || v.length() == 0) {//
			return rt;
		}
		String[] ss = v.split(",");
		rt.addAll(Arrays.asList(ss));//
		return rt;
	}

	/*
	 * Nov 29, 2012
	 */
	@Override
	public HasProperties<T> convert(String[] from, boolean[] force, String[] to) {
		//
		HasProperties<T> rt = new MapProperties<T>();
		for (int i = 0; i < from.length; i++) {
			T value = this.getProperty(from[i], force[i]);
			rt.setProperty(to[i], value);
		}
		return rt;
	}

	@Override
	public boolean isContainsSameProperties(Object... kvs) {
		HasProperties<T> pts = MapProperties.valueOf(kvs);
		return this.isContainsSameProperties(pts);
	}

	@Override
	public boolean isContainsSameProperties(HasProperties<T> kvs) {
		List<String> ks1 = kvs.keyList();
		for (String k : ks1) {
			Object o1 = this.getProperty(k);
			Object o2 = kvs.getProperty(k);
			if (!ObjectUtil.isNullSafeEquals(o1, o2)) {
				return false;
			}
		}
		return true;

	}

	/*
	 * Dec 13, 2012
	 */
	@Override
	public T getPropertyWithDefault(String key, T def) {
		T rt = this.getProperty(key);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	@Override
	public T getProperty(String key, boolean force) {
		//
		T rt = this.getProperty(key);
		if (rt == null && force) {
			throw new RuntimeException("no property value for key:" + key + ",keyList:" + this.keyList());
		}
		return rt;
	}
}
