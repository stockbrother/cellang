package org.cellang.console.chart;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultXLabelRenderer<T> implements XLabelRenderer<T> {

	public static SimpleDateFormat DF = new SimpleDateFormat("yyyy/MM/dd");

	@Override
	public String toDisplayValue(T value) {
		if (value == null) {
			return "null";
		}
		if (value instanceof Date) {
			return DF.format((Date) value);
		}
		return String.valueOf(value);
	}

}
