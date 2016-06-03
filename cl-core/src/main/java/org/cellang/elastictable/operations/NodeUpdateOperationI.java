/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elastictable.operations;

import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.result.BooleanResultI;

/**
 * @author wu TODO separate a Query interface for different query style.
 */
public interface NodeUpdateOperationI<W extends RowObject> extends
		NodeOperationI<NodeUpdateOperationI<W>, W, BooleanResultI> {
	
	public static final String PK_REFRESH_AFTER_UPDATE = "refreshAfterUpdate";
	
	public NodeUpdateOperationI<W> property(String key, Object value);

	public NodeUpdateOperationI<W> uniqueId(String uid);

	public String getUniqueId();
	
	public NodeUpdateOperationI<W> refreshAfterUpdate(boolean refreshAfterUpdate);


}
