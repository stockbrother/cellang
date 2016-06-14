package org.cellang.core.rowobject;

import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.meta.DataSchema;

public class RowRowObject extends RowObject {

	public static final String SHEETID = "sheetId";

	public static final String NEXTROWID = "nextRowId";

	/**
	 * @param pts
	 */
	public RowRowObject() {
		super(RowObjectTypes.ROW);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(RowObjectTypes.ROW, RowRowObject.class).field(SHEETID).field(NEXTROWID);
	}

	public void setSheetId(String value) {
		this.setProperty(SHEETID, value);
	}

	public String getSheetId() {
		return (String) this.getProperty(SHEETID);
	}

	public void setNextRowId(String value) {
		this.setProperty(NEXTROWID, value);
	}

	public String getNextRowId() {
		return (String) this.target.getProperty(NEXTROWID);
	}

}
