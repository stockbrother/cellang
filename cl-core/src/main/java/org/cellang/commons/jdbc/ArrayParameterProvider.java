package org.cellang.commons.jdbc;

public class ArrayParameterProvider implements ParameterProvider {

	private Object[] values;

	public ArrayParameterProvider(Object[] pA) {
		this.values = pA;
	}

	@Override
	public int size() {
		return this.values.length;
	}

	@Override
	public Object get(int idx) {
		return this.values[idx];
	}

}