package org.cellang.core;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.cellang.core.converter.DateStringConverter;
import org.cellang.core.entity.Converter;

public class Main {
	private static Map<Class, Converter> converterMap = new HashMap<Class, Converter>();

	static {
		converterMap.put(Date.class, new DateStringConverter("yyyy/MM/dd"));
	}

	public static void main(String[] args) throws IOException {
		clojure.main.main(args);		
	}

}
