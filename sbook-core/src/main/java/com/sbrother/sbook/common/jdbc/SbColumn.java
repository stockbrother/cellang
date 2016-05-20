package com.sbrother.sbook.common.jdbc;

public class SbColumn {

	protected String name;

	public SbColumn(String name) {
		this.name = name;
	}

	public static SbColumn getInstance(String string) {
		//
		return new SbColumn(string);
	}
}
