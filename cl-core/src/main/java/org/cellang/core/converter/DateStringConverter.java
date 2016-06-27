package org.cellang.core.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.cellang.core.entity.Converter;

public class DateStringConverter implements Converter {
	private SimpleDateFormat format;

	public DateStringConverter(String format) {
		this.format = new SimpleDateFormat(format);
	}

	@Override
	public Object convert(Object obj) {
		//
		if (obj == null) {
			return null;
		}
		Date date = (Date) obj;

		return format.format(date);
	}

}
