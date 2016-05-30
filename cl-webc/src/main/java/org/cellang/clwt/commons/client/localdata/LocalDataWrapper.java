/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package org.cellang.clwt.commons.client.localdata;

/**
 * @author wu
 * 
 */
public class LocalDataWrapper {

	protected LocalData target;

	public LocalDataWrapper(String name) {
		LocalStore ls = LocalStore.getInstance();

		this.target = ls.getData(name);
	}

	public LocalDataWrapper(LocalData ld) {
		this.target = ld;
	}

	/**
	 * @return the target
	 */
	public LocalData getTarget() {
		return target;
	}

	public String getValue() {
		return this.target.getValue();
	}

	public int getValueAsInt(int def) {
		return this.target.getValueAsInt(def);
	}

	public boolean getValueAsBoolean() {
		return this.target.getValueAsBoolean();
	}

	public LocalData getChild(String name) {
		return this.target.getChild(name);//
	}

	public String getValue(String name) {
		return this.getChild(name).getValue();
	}

	public boolean getValueAsBoolean(String name) {
		return this.getChild(name).getValueAsBoolean();
	}

	public int getValueAsInt(String name, int def) {
		return this.getChild(name).getValueAsInt(def);
	}

	public void setValue(String name, boolean value) {
		this.setValue(name, value + "");
	}

	public void setValue(String name, String value) {
		this.getChild(name).setValue(value);
	}

	public String getKey() {
		return this.target.getKey();
	}
}
