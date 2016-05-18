package com.sb.anyattribute.core;

public interface AttributeSource {

	public void open();

	public void close();

	public AttributeList getAttributeList(AttributeOwner ao, AttributeDate date);

	public AttributeObject getAttributeObject(AttributeOwner ao, AttributeDate date, AttributeName name);

	public void saveAttributeObject(AttributeObject ao);

	public void clear();
}
