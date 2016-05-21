package org.cellang.core.jdbc;

import org.cellang.common.jdbc.TableColumn;

public class PropertiesTable {

	public static final String T_PROPERTIES = "properties";

	public static final TableColumn F_NAME = TableColumn.getInstance("name");
	public static final TableColumn F_VALUE = TableColumn.getInstance("value");

}
