/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 30, 2012
 */
package org.cellang.elastictable.test;

import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.meta.AnalyzerType;
import org.cellang.elastictable.meta.DataSchema;

/**
 * @author wu
 * 
 */
public class MockObject extends RowObject {

	
	public static final String FIELD1 = "field1";
	public static final String FIELD2 = "field2";
	public static final String FIELD3 = "field3";

	/**
	 * @param type
	 */
	public MockObject() {
		super("Mock");
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig("Mock", MockObject.class).field("field1").field("field2", AnalyzerType.TEXT)
				.field(FIELD3, AnalyzerType.TEXT);

	}

}
