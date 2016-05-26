/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 23, 2012
 */
package org.cellang.clwt.core.client.lang;

/**
 * @author wu
 *
 */
public class State {
	
	private String value;
	
	protected State(String v){
		this.value = v;
	}
	
	public static State valueOf(String v){
		return new State(v);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof State)){
			return false;
		}
		return this.value.equals(((State)obj).value);
	}
	
	
}
