package org.cellang.core.entity;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVWriter;

public class EntityCsvWriter {
	private CSVWriter cw;
	EntityConfig ec;
	Map<Class, Converter> cmap;

	public EntityCsvWriter(Writer w, EntityConfig ec, Map<Class, Converter> cmap) {
		this.cw = new CSVWriter(w, ',', CSVWriter.NO_QUOTE_CHARACTER);
		this.ec = ec;
		this.cmap = cmap;
	}

	public <T extends EntityObject> void write(List<T> el) {
		List<Method> mL = ec.getGetMethodList();
		Converter[] converters = new Converter[mL.size()];
		for (int i = 0; i < mL.size(); i++) {
			Class cls = mL.get(i).getReturnType();
			converters[i] = cmap.get(cls);
		}

		for (T m : el) {

			String[] str = new String[mL.size()];
			for (int i = 0; i < str.length; i++) {
				Method me = mL.get(i);
				Object value = null;
				try {
					value = me.invoke(m);

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
				str[i] = converters[i] == null ? String.valueOf(value) : String.valueOf(converters[i].convert(value));
			}
			cw.writeNext(str);
		}
	}
	
	public void close(){
		try {
			this.cw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
