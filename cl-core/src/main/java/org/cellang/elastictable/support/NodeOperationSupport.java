/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.support;

import org.cellang.elastictable.ExecuteResult;
import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.meta.NodeMeta;
import org.cellang.elastictable.operations.NodeOperationI;

/**
 * @author wu
 * 
 */
public abstract class NodeOperationSupport<O extends NodeOperationI<O, W, T>, W extends RowObject, T extends ExecuteResult<T, ?>>
		extends OperationSupport<O, T> implements NodeOperationI<O, W, T> {

	public static final String PK_NODETYPE = "nodeType";

	public static final String PK_WRAPPER_CLS = "wrapperClass";

	protected NodeMeta nodeConfig;

	/**
	 * @param ds
	 */
	public NodeOperationSupport(T rst) {
		super(rst);

	}

	@Override
	public O nodeType(String ntype) {

		NodeMeta nc = this.dataService.getConfigurations().getNodeConfig(ntype, true);

		return this.nodeType(nc);
	}

	@Override
	public O nodeType(Class<W> cls) {
		NodeMeta nc = this.dataService.getConfigurations().getNodeConfig(cls, true);

		return this.nodeType(nc);
	}

	protected O nodeType(NodeMeta nc) {
		this.nodeConfig = nc;
		this.parameter(PK_NODETYPE, nc.getNodeType());
		this.parameter(PK_WRAPPER_CLS, nc.getWrapperClass());
		return this.cast();
	}

	public String getNodeType(boolean force) {
		return (String) this.getParameter(PK_NODETYPE, force);
	}

}
