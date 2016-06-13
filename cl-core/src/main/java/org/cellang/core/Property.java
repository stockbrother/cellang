/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.core;

import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.meta.DataSchema;

/**
 * @author wu
 * 
 */
public class Property extends RowObject {

	public static final String OWNER = "owner";

	public static final String KEY = "key";

	public static final String VALUE = "value";

	/**
	 * @param pts
	 */
	public Property() {
		super(NodeTypes.PROPERTY);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.PROPERTY, Property.class).field(OWNER).field(KEY).field(VALUE);
	}

	public void setOwner(String owner) {
		this.setProperty(OWNER, owner);
	}

	public void setKey(String value) {
		this.setProperty(KEY, value);
	}

	public void setValue(Object value) {
		this.setProperty(VALUE, value);
	}

}
