package com.sbrother.sbook.core;

public interface SbCellSource {

	public void open();

	public void close();

	public SbCellValue getCell(String book, String name);

	public void save(SbCellValue ao);

	public void clear();
}
