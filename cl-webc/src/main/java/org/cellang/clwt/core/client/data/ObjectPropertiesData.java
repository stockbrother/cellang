/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client.data;

import org.cellang.clwt.core.client.UiException;

/**
 * @author wu
 * 
 */
public class ObjectPropertiesData extends PropertiesData<Object> {

	public void setString(String key, String value) {
		this.setProperty(key, (value));
	}

	public String getString(String key, boolean force) {
		String rt = this.getString(key);

		if (rt == null && force) {
			throw new UiException("no value for key:" + key + " in properties data:" + this);
		}
		return rt;
	}

	public String getString(String key) {
		String sd = (String) this.getProperty(key);
		return sd;
	}

	/* */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ObjectPropertiesData)) {
			return false;
		}

		return super.isEquals((ObjectPropertiesData) obj);

	}

	/**
	 * Jan 2, 2013
	 */
	public Boolean getBoolean(String key, Boolean def) {
		//
		Boolean rt = (Boolean) this.getProperty(key);

		return rt == null ? def : rt;
	}
}
