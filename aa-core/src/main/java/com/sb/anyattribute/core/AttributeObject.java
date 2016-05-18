package com.sb.anyattribute.core;

public class AttributeObject {

	private AttributeOwner owner;

	private AttributeDate date;

	private AttributeName name;

	private AttributeValue value;

	public AttributeOwner getOwner() {
		return owner;
	}

	public AttributeDate getDate() {
		return date;
	}

	public AttributeName getName() {
		return name;
	}

	public AttributeValue getValue() {
		return value;
	}

	public void setOwner(AttributeOwner owner) {
		this.owner = owner;
	}

	public void setDate(AttributeDate date) {
		this.date = date;
	}

	public void setName(AttributeName name) {
		this.name = name;
	}

	public void setValue(AttributeValue value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AttributeObject)) {
			return false;
		}
		AttributeObject ao = (AttributeObject) obj;

		return AttributeOwner.isNullSafeEquals(this.owner, ao.owner) //
				&& AttributeDate.isNullSafeEquals(this.date, ao.date)//
				&& AttributeName.isNullSafeEquals(this.name, ao.name) //
				&& AttributeValue.isNullSafeEquals(this.value, ao.value)//
		;
	}

}
