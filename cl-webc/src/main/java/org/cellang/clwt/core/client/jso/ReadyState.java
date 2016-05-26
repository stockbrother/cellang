/**
 *  Jan 15, 2013
 */
package org.cellang.clwt.core.client.jso;

/**
 * @author wuzhen
 * 
 */
public class ReadyState {
	public static ReadyState CONNECTING = new ReadyState((short) 0, "CONNECTING");
	public static ReadyState OPEN = new ReadyState((short) 1, "OPEN");
	public static ReadyState CLOSING = new ReadyState((short) 2, "CLOSING");
	public static ReadyState CLOSED = new ReadyState((short) 3, "CLOSED");

	private short value;

	private String name;

	private ReadyState(short value, String name) {
		this.value = value;
		this.name = name;
	}

	public static ReadyState getReadyState(short value) {
		switch (value) {
		case 0:
			return CONNECTING;
		case 1:
			return OPEN;
		case 2:

			return CLOSING;
		case 3:
			return CLOSED;
		default:
			return new ReadyState(value, "UNKOWN");
		}
	}

	public boolean isOpen() {
		return this.value == OPEN.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ReadyState)) {
			return false;
		}
		ReadyState rs = (ReadyState) obj;
		return rs.value == this.value;
	}

	public String toString() {
		return this.name + "(" + this.value + ")";
	}
}
