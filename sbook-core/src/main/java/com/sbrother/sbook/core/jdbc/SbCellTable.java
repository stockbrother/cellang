package com.sbrother.sbook.core.jdbc;

import com.sbrother.sbook.common.jdbc.SbColumn;
import com.sbrother.sbook.common.jdbc.SbSqlOperation;

public class SbCellTable {

	public static final String T_CELLS = "cells";
	
	public static final SbColumn F_BOOKID = SbColumn.getInstance("bookid");
	public static final SbColumn F_TYPEID = SbColumn.getInstance("typeid");

	public static final SbSqlOperation SQL_CREATE = SbSqlOperation
			.getInstance("create table if not exists " + T_CELLS + "(bookid varchar,typeid varchar,value decimal) ");
	public static final SbSqlOperation SQL_INSERT = SbSqlOperation
			.getInstance("insert into " + T_CELLS + "(bookid,typeid,value)values(?,?,?)", 3);
	
	public static final SbSqlOperation SQL_DROP = SbSqlOperation.getInstance("drop all objects");
	
	public static final SbSqlOperation SQL_DELETE = SbSqlOperation.getInstance("delete from " + T_CELLS);

}
