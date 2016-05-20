package com.sbrother.sbook.core;

import com.sbrother.sbook.common.ObjectUtil;

public class SbCellValue {

	private String bookId;

	private String typeId;

	private SbValue value;

	private long version;

	public String getBookId() {
		return bookId;
	}

	public String getTypeId() {
		return typeId;
	}

	public SbValue getValue() {
		return value;
	}

	public void setBookId(String bid) {
		this.bookId = bid;
	}

	public void setTypeId(String name) {
		this.typeId = name;
	}

	public void setValue(SbValue value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SbCellValue)) {
			return false;
		}
		SbCellValue ao = (SbCellValue) obj;

		return ObjectUtil.isNullSafeEquals(this.bookId, ao.bookId)//
				&& ObjectUtil.isNullSafeEquals(this.typeId, ao.typeId) //
				&& ObjectUtil.isNullSafeEquals(this.value, ao.value)//
		;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
