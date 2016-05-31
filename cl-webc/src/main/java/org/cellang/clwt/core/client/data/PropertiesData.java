/**
 * Jun 13, 2012
 */
package org.cellang.clwt.core.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.util.ObjectUtil;

/**
 * @author wuzhen
 * 
 */
public class PropertiesData<T>  {
	private Map<String, T> map = new HashMap<String, T>();

	public List<String> keyList() {
		List<String> rt = new ArrayList<String>();
		rt.addAll(map.keySet());
		return rt;
	}

	public void setProperties(PropertiesData<T> pts) {
		this.map.putAll(pts.map);//
	}

	public T getProperty(String pname) {
		return this.getProperty(pname, false);
	}

	public void setProperty(String key, T value) {
		this.map.put(key, value);
	}

	public T removeProperty(String key) {
		return this.map.remove(key);
	}

	public T getProperty(String key, boolean force) {
		T rt = this.getProperty(key, null);
		if (rt == null && force) {
			throw new UiException("no property:" + key);
		}
		return rt;
	}

	public T getProperty(String key, T def) {
		T rt = this.map.get(key);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	@Override
	public String toString() {
		return this.map.toString();
	}

	protected boolean isEquals(PropertiesData<T> pts) {
		if (pts == null) {
			return false;
		}
		List<String> kl = pts.keyList();

		if (this.keyList().size() != kl.size()) {
			return false;
		}

		for (String key : this.keyList()) {
			T d = this.getProperty(key);
			T d2 = pts.getProperty(key);
			if (!ObjectUtil.nullSafeEquals(d, d2)) {
				return false;
			}
		}
		return true;
	}

}
