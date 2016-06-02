/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.operations;

import org.cellang.core.lang.HasProperties;
import org.cellang.elasticnode.NodeType;
import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.result.NodeCreateResultI;

/**
 * @author wu
 * 
 */
public interface NodeCreateOperationI<W extends NodeWrapper> extends
		NodeOperationI<NodeCreateOperationI<W>, W, NodeCreateResultI> {

	public static final String PK_REFRESH_AFTER_CREATE = "refreshAfterCreate";

	public NodeCreateOperationI<W> refreshAfterCreate(boolean refreshAfterCreate);

	public NodeCreateOperationI<W> execute(NodeType type, HasProperties<Object> pts);

	public NodeCreateOperationI<W> uniqueId(String id);

	public NodeCreateOperationI<W> properties(HasProperties<Object> pts);

	public NodeCreateOperationI<W> property(String key, Object value);

	public HasProperties<Object> getNodeProperties();

	public <T extends NodeWrapper> NodeCreateOperationI<W> wrapper(T nw);

}
