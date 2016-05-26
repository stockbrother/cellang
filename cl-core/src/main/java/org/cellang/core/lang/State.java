/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 17, 2012
 */
package org.cellang.core.lang;

/**
 * @author wu
 * 
 */
public class State {

	private String name;

	public State(String name) {
		this.name = name;
	}

	/*
	 * Dec 17, 2012
	 */
	@Override
	public int hashCode() {
		//
		return this.name.hashCode();
	}

	/*
	 * Dec 17, 2012
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof State)) {
			return false;
		}
		return ((State) obj).name.equals(this.name);

	}

}
