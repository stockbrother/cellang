package org.cellang.core.balancesheet;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapWrapper {
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd");

	public Map<String, String> map = new HashMap<String, String>();

	public MapWrapper() {

	}

	public void put(String key, String value) {
		this.map.put(key, value);
	}

	public String get(String key, boolean force) {
		String rt = map.get(key);
		if (rt == null && force) {
			throw new RuntimeException("no value for key:" + key);
		}
		return rt;

	}

	public BigDecimal getAsBigDecimal(String key, boolean force) {
		String valueS = map.get(key);
		if (valueS == null) {
			if (force) {
				throw new RuntimeException("no value for key:" + key);
			}
			return null;
		}
		return new BigDecimal(valueS);
	}

	public Date getAsDate(String key) {
		String valueS = this.get(key, true);
		Date rt;
		try {
			rt = FORMAT.parse(valueS);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return rt;

	}

}
