package org.cellang.core.balancesheet;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CsvRowMap {
	
	public Map<String, CsvRow> map = new HashMap<String, CsvRow>();

	public CsvRowMap() {

	}

	public void put(String key, CsvRow value) {
		this.map.put(key, value);
	}

	public CsvRow get(String key, boolean force) {
		CsvRow rt = map.get(key);
		if (rt == null && force) {
			throw new RuntimeException("no value for key:" + key);
		}
		return rt;

	}

}
