package com.sbrother.sbook.core;

public interface SbCellDataSource {

	public void open();

	public void close();

	public SbCellList getAttributeList(SbOwner ao, SbDate date);

	public SbCell getAttributeObject(SbOwner ao, SbDate date, SbName name);

	public void saveAttributeObject(SbCell ao);

	public void clear();
}
