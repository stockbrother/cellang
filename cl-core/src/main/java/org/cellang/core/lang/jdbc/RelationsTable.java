package org.cellang.core.lang.jdbc;

import org.cellang.commons.jdbc.SqlOperation;
import org.cellang.commons.jdbc.TableColumn;

public class RelationsTable {

	public static final String T_RELATIONS = "relations";

	public static final TableColumn F_ID1 = TableColumn.getInstance("id1");
	public static final TableColumn F_TYPEID = TableColumn.getInstance("typeid");
	public static final TableColumn F_ID2 = TableColumn.getInstance("id2");

	public static char FV_CHILD = 'c';

}
