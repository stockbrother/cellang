package org.cellang.commons;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

	private Map<String, String> valueMap = new HashMap<String, String>();

	public void set(String key, String value) {
		this.valueMap.put(key, value);
	}

	public String getString(String string) {
		return getString(string, false);
	}

	public String getString(String string, String def) {
		String rt = valueMap.get(string);
		if (rt == null) {
			return def;
		}
		return rt;
	}

	public String getString(String string, boolean force) {
		String rt = valueMap.get(string);
		if (rt == null && force) {

			throw new RuntimeException("no value found for key:" + string);
		}
		return rt;

	}

	public int getAsInt(String string, int i) {
		String valueS = this.getString(string);
		if (valueS == null) {
			return i;
		}
		return Integer.parseInt(valueS);//
	}

	public boolean getBoolean(String string, boolean b) {
		String valueS = this.getString(string);
		if (valueS == null) {
			return b;
		}
		return Boolean.valueOf(valueS);
	}

}
