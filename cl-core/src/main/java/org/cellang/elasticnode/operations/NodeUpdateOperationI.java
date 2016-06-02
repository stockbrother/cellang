/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elasticnode.operations;

import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.result.BooleanResultI;

/**
 * @author wu TODO separate a Query interface for different query style.
 */
public interface NodeUpdateOperationI<W extends NodeWrapper> extends
		NodeOperationI<NodeUpdateOperationI<W>, W, BooleanResultI> {
	
	public static final String PK_REFRESH_AFTER_UPDATE = "refreshAfterUpdate";
	
	public NodeUpdateOperationI<W> property(String key, Object value);

	public NodeUpdateOperationI<W> uniqueId(String uid);

	public String getUniqueId();
	
	public NodeUpdateOperationI<W> refreshAfterUpdate(boolean refreshAfterUpdate);


}
