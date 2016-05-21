package com.sbrother.sbook.common.jdbc;

public class SbArrayParameterProvider implements SbParameterProvider {

	private Object[] values;

	public SbArrayParameterProvider(Object[] pA) {
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