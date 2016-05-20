package com.sb.anyattribute.core.jdbc;

public class AttributeTable {

	public static final String SQL_CREATE = "create table if not exists attributes(owner varchar,date timestamp,name varchar,value decimal) ";
	public static final String SQL_INSERT = "insert into attributes(owner,date,name,value)values(?,?,?,?)";
	public static final String SQL_SELECT = "select owner,date,name,value from attributes where owner=? and date=? and name=?";
	public static final String SQL_DROP = "drop all objects";
	public static final String SQL_DELETE = "delete from attributes";

}
