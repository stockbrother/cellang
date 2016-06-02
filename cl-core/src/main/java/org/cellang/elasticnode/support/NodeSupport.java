/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.support;

import org.cellang.core.lang.MapProperties;
import org.cellang.elasticnode.Node;
import org.cellang.elasticnode.NodeType;

/**
 * @author wu
 * 
 */
public class NodeSupport extends MapProperties<Object> implements Node {

	public NodeSupport(NodeType type, String uid) {
		this.setProperty(PK_TYPE, type);
		this.setProperty(PK_UNIQUE_ID, uid);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public String getUniqueId() {
		//
		return (String) this.getProperty(PK_UNIQUE_ID);
	}

	@Override
	public String getId() {
		//
		return (String) this.getProperty(PK_ID);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeType getType() {
		//
		return (NodeType) this.getProperty(PK_TYPE);

	}

	/*
	 *Nov 18, 2012
	 */
	@Override
	public String getTimestamp() {
		// 
		return (String)this.getProperty(PK_TIMESTAMP);
		
	}

}
