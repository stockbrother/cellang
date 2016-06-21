package org.cellang.webc.main.client.data;

import java.util.ArrayList;
import java.util.List;

import org.cellang.clwt.core.client.data.ObjectPropertiesData;

public class TableData {

	private List<List<Object>> rowList = new ArrayList<List<Object>>();

	private List<ObjectPropertiesData> columnDefList = new ArrayList<ObjectPropertiesData>();

	public List<ObjectPropertiesData> getColumnDefList() {
		return this.columnDefList;
	}

	public String getColumnName(int i) {
		return this.getColumnDef(i).getString("name");
	}

	public String getColumnType(int i) {
		return this.getColumnDef(i).getString("type");
	}

	public ObjectPropertiesData getColumnDef(int i) {
		return this.columnDefList.get(i);
	}

	public int getCols() {
		return this.columnDefList.size();
	}

	public int getRows() {
		return this.rowList.size();
	}

	public Object get(int i, int j) {
		List<Object> row = this.getRow(i);
		return row.get(j);//

	}

	public List<Object> getRow(int idx) {
		if (idx >= this.rowList.size()) {
			return null;
		}
		return this.rowList.get(idx);
	}

}
