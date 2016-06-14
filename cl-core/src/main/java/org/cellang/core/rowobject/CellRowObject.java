/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.core.rowobject;

import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.meta.DataSchema;

/**
 * @author wu
 * 
 */
public class CellRowObject extends RowObject {

	public static final String SHEETID = "sheetId";

	public static final String ROWID = "rowId";

	public static final String COLID = "colId";

	public static final String VALUE = "value";

	/**
	 * @param pts
	 */
	public CellRowObject() {
		super(RowObjectTypes.CELL);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(RowObjectTypes.CELL, CellRowObject.class).field(SHEETID).field(ROWID).field(COLID).field(VALUE);		
	}

	public void setSheetId(String owner) {
		this.setProperty(SHEETID, owner);
	}

	public String getSheetId() {
		return (String) this.getProperty(SHEETID);
	}

	public void setRowId(String owner) {
		this.setProperty(ROWID, owner);
	}

	public String getRowId() {
		return (String) this.getProperty(ROWID);
	}

	public void setColId(String owner) {
		this.setProperty(COLID, owner);
	}

	public String getColId() {
		return (String) this.getProperty(COLID);
	}

	public void setValue(String owner) {
		this.setProperty(VALUE, owner);
	}

	public String getValue() {
		return (String) this.getProperty(VALUE);
	}

}
