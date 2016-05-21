package org.cellang.core;

import java.util.List;

public interface CellSource {

	public void open();

	public void close();

	public CellRecord getCell(String cellId);

	public List<CellRecord> getCellList(String parentId, String typeId);

	public String save(CellRecord ao);

	public void saveRelation(String id1, char typeid, String id2);

	public void clear();
	
	public void destroy();
}
