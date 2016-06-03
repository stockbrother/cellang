/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.operations;

import org.cellang.core.lang.HasProperties;
import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.result.NodeCreateResultI;

/**
 * @author wu
 * 
 */
public interface NodeCreateOperationI<W extends RowObject> extends
		NodeOperationI<NodeCreateOperationI<W>, W, NodeCreateResultI> {

	public static final String PK_REFRESH_AFTER_CREATE = "refreshAfterCreate";

	public NodeCreateOperationI<W> refreshAfterCreate(boolean refreshAfterCreate);

	public NodeCreateOperationI<W> execute(String type, HasProperties<Object> pts);

	public NodeCreateOperationI<W> uniqueId(String id);

	public NodeCreateOperationI<W> properties(HasProperties<Object> pts);

	public NodeCreateOperationI<W> property(String key, Object value);

	public HasProperties<Object> getNodeProperties();

	public <T extends RowObject> NodeCreateOperationI<W> wrapper(T nw);

}
