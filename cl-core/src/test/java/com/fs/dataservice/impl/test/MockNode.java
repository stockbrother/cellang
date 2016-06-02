/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 30, 2012
 */
package com.fs.dataservice.impl.test;

import org.cellang.elasticnode.NodeType;
import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.meta.AnalyzerType;
import org.cellang.elasticnode.meta.DataSchema;

/**
 * @author wu
 * 
 */
public class MockNode extends NodeWrapper {

	public static NodeType TYPE = NodeType.valueOf("mockNode");

	public static final String FIELD1 = "field1";
	public static final String FIELD2 = "field2";
	public static final String FIELD3 = "field3";

	/**
	 * @param type
	 */
	public MockNode() {
		super(TYPE);
	}

	public static void config(DataSchema cfs) {
		cfs.addConfig(TYPE, MockNode.class).field("field1").field("field2", AnalyzerType.TEXT)
				.field(FIELD3, AnalyzerType.TEXT);

	}

}
