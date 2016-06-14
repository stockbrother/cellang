package org.cellang.core.rowobject;

import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.meta.DataSchema;

public class ColumnRowObject extends RowObject {
	public static final String SHEETID = "sheetId";

	public static final String NEXTCOLID = "nextColId";

	public ColumnRowObject() {
		super(RowObjectTypes.COL);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(RowObjectTypes.COL, RowRowObject.class).field(SHEETID).field(NEXTCOLID);
	}

	public void setSheetId(String value) {
		this.setProperty(SHEETID, value);
	}

	public String getSheetId() {
		return (String) this.getProperty(SHEETID);
	}

	public void setNextColId(String value) {
		this.setProperty(NEXTCOLID, value);
	}

	public String getNextColId() {
		return (String) this.target.getProperty(NEXTCOLID);
	}

}
