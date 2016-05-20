package com.sbrother.sbook.core;

public class SbCell {

	private SbOwner owner;

	private SbDate date;

	private SbName name;

	private SbValue value;

	public SbOwner getOwner() {
		return owner;
	}

	public SbDate getDate() {
		return date;
	}

	public SbName getName() {
		return name;
	}

	public SbValue getValue() {
		return value;
	}

	public void setOwner(SbOwner owner) {
		this.owner = owner;
	}

	public void setDate(SbDate date) {
		this.date = date;
	}

	public void setName(SbName name) {
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

		return SbOwner.isNullSafeEquals(this.owner, ao.owner) //
				&& SbDate.isNullSafeEquals(this.date, ao.date)//
				&& SbName.isNullSafeEquals(this.name, ao.name) //
				&& SbValue.isNullSafeEquals(this.value, ao.value)//
		;
	}

}
