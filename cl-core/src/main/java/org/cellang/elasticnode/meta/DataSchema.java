/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 2, 2012
 */
package org.cellang.elasticnode.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.elasticnode.NodeType;
import org.cellang.elasticnode.NodeWrapper;

/**
 * @author wu
 * 
 */
public class DataSchema {

	private boolean freeze;

	private Map<NodeType, NodeMeta> nodeCofigMap = new HashMap<NodeType, NodeMeta>();
	private Map<Class<?>, NodeMeta> nodeCofigMap2 = new HashMap<Class<?>, NodeMeta>();

	public void freeze() {
		this.freeze = true;
	}

	public List<NodeMeta> getNodeMetaList() {
		return new ArrayList<NodeMeta>(this.nodeCofigMap.values());
	}

	public NodeMeta getNodeConfig(NodeType ntype, boolean force) {
		NodeMeta rt = this.nodeCofigMap.get(ntype);
		if (rt == null && force) {
			throw new RuntimeException("no config for node type:" + ntype);
		}
		return rt;
	}

	public NodeMeta addConfig(NodeType ntype, Class<? extends NodeWrapper> wcls) {
		NodeMeta rt = new NodeMeta(ntype, wcls);
		this.addNodeMeta(rt);
		return rt;
	}

	public void addNodeMeta(NodeMeta nm) {
		if (this.freeze) {
			throw new RuntimeException("schema is freezed");
		}

		NodeType ntype = nm.getNodeType();
		Class wcls = nm.getWrapperClass();
		if (this.getNodeConfig(ntype, false) != null) {
			throw new RuntimeException("already exist config for type:" + ntype);
		}

		this.nodeCofigMap.put(ntype, nm);
		this.nodeCofigMap2.put(wcls, nm);

	}

	/**
	 * Nov 28, 2012
	 */
	public NodeMeta getNodeConfig(Class<? extends NodeWrapper> cls, boolean force) {
		NodeMeta rt = this.nodeCofigMap2.get(cls);
		if (rt == null && force) {
			throw new RuntimeException("no config for wrapper:" + cls + ",all config:"
					+ this.nodeCofigMap2.keySet());
		}
		return rt;

	}

}
