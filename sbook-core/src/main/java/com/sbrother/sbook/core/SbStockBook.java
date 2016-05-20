package com.sbrother.sbook.core;

public interface SbStockBook {

	public void open();

	public void close();

	public SbCell getCell(String book, String name);

	public void saveAttributeObject(SbCell ao);

	public void clear();
}
