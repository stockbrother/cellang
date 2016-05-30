/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package org.cellang.clwt.commons.client.localdata;

/**
 * @author wu
 * 
 */
public class LocalData {

	private String key;

	private LocalStore localStore;

	public LocalData(String key, LocalStore ls) {
		this.key = key;
		this.localStore = ls;
	}

	public void setValue(String value) {
		this.localStore.setValue(this.key, value);
	}

	public String getValue() {
		return this.localStore.getValue(this.key);
	}

	public int getValueAsInt(int def) {
		String v = this.getValue();
		if (v == null) {
			return def;
		}
		return Integer.parseInt(this.getValue());
	}

	public boolean getValueAsBoolean() {
		return Boolean.valueOf(this.getValue());
	}

	public LocalData getChild(String name) {
		String ckey = this.key + "." + name;
		return new LocalData(ckey, this.localStore);
	}

	public String getKey() {
		return this.key;
	}

}
