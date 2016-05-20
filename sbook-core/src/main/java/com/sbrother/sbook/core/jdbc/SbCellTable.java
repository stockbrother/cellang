package com.sbrother.sbook.core.jdbc;

import com.sbrother.sbook.common.jdbc.SbSql;

public class SbCellTable {

	public static final SbSql SQL_CREATE = SbSql.getInstance(
			"create table if not exists attributes(owner varchar,date timestamp,name varchar,value decimal) ");
	public static final SbSql SQL_INSERT = SbSql
			.getInstance("insert into attributes(owner,date,name,value)values(?,?,?,?)", 4);
	public static final SbSql SQL_SELECT = SbSql
			.getInstance("select owner,date,name,value from attributes where owner=? and date=? and name=?", 3);
	public static final SbSql SQL_DROP = SbSql.getInstance("drop all objects");
	public static final SbSql SQL_DELETE = SbSql.getInstance("delete from attributes");

}
