/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package org.cellang.elasticnode;

/**
 * @author wu
 * 
 */
public interface Node extends NodeRow {

	// NOTE the property cannot start with '_' for elastic reserved some
	// properties such as '_type' '_uid' etc.

	public static final String PK_UNIQUE_ID = "uniqueId_";// system global
															// unique.
	public static final String PK_TYPE = "type_";
	public static final String PK_ID = "id_";// business id set from outer
	public static final String PK_TIMESTAMP = "timestamp_";
	public static final String PK_EXTEND1="extend1_";
	public static final String PK_EXTEND2="extend2_";
	public static final String PK_EXTEND3="extend3_";
	public static final String PK_EXTEND4="extend4_";
	public static final String PK_EXTEND5="extend5_";
	public static final String PK_EXTEND6="extend6_";
	public static final String PK_EXTEND7="extend7_";
	public static final String PK_EXTEND8="extend8_";
	public static final String PK_EXTEND9="extend9_";
	

	public String getUniqueId();// unique in the same type.

	public String getId();// another id that may assigned by user.

	public String getTimestamp();

	public NodeType getType();

}
