package org.cellang.core.rowobject;

import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.meta.DataSchema;

public class SheetRowObject extends RowObject {

	public static final String OWNER = "owner";

	public static final String NAME = "name";

	/**
	 * @param pts
	 */
	public SheetRowObject() {
		super(RowObjectTypes.SHEET);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(RowObjectTypes.SHEET, SheetRowObject.class).field(OWNER).field(NAME);
	}

	public void setOwner(String Owner) {
		this.setProperty(OWNER, Owner);
	}

	public String getOwner() {
		return (String) this.getProperty(OWNER);
	}

	public void setName(String password) {
		this.setProperty(NAME, password);
	}

	public String getName() {
		return (String) this.target.getProperty(NAME);
	}

}
