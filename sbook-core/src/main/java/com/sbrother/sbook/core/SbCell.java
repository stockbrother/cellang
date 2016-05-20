package com.sbrother.sbook.core;

import com.sbrother.sbook.common.ObjectUtil;

public class SbCell {

	private String book;

	private String name;

	private SbValue value;

	private long version;

	public String getBookIdentifier() {
		return book;
	}

	public String getName() {
		return name;
	}

	public SbValue getValue() {
		return value;
	}

	public void setBookIdentifier(String date) {
		this.book = date;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(SbValue value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SbCell)) {
			return false;
		}
		SbCell ao = (SbCell) obj;

		return ObjectUtil.isNullSafeEquals(this.book, ao.book)//
				&& ObjectUtil.isNullSafeEquals(this.name, ao.name) //
				&& SbValue.isNullSafeEquals(this.value, ao.value)//
		;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
