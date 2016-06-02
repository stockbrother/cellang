/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.support;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.cellang.commons.util.ClassUtil;
import org.cellang.elasticnode.NodeOperation;
import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.NodeType;
import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.meta.DataSchema;
import org.cellang.elasticnode.operations.NodeCountOperationI;
import org.cellang.elasticnode.operations.NodeDeleteOperationI;
import org.cellang.elasticnode.operations.NodeGetOperationI;
import org.cellang.elasticnode.operations.NodeSearchOperationI;
import org.cellang.elasticnode.operations.NodeUpdateOperationI;
import org.cellang.elasticnode.result.BooleanResultI;
import org.cellang.elasticnode.result.NodeResultI;
import org.cellang.elasticnode.result.NodeSearchResultI;

/**
 * @author wu
 * 
 */
public abstract class NodeServiceSupport implements NodeService {

	protected DataSchema schema;

	protected Map<String, Class<? extends NodeOperation>> operationInterfaceMap;

	protected Map<Class<? extends NodeOperation>, Class<? extends NodeOperation>> operationInterfaceImplementMap;

	public NodeServiceSupport(DataSchema ds) {
		this.schema = ds;
		this.operationInterfaceImplementMap = new HashMap<Class<? extends NodeOperation>, Class<? extends NodeOperation>>();
		this.operationInterfaceMap = new HashMap<String, Class<? extends NodeOperation>>();
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends NodeOperation> T prepareOperation(String name) {
		//
		Class<? extends NodeOperation> cls = this.operationInterfaceMap.get(name);
		return (T) this.prepareOperation(cls);

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends NodeOperation> T prepareOperation(Class<T> opc) {
		//
		Class<? extends T> cls2 = (Class<? extends T>) this.operationInterfaceImplementMap.get(opc);
		if (cls2 == null) {
			throw new RuntimeException("no impl class for:" + opc);
		}
		T rt = this.perpareOperationInternal(opc, cls2);
		rt.prepare();//
		return rt;
	}

	public abstract <T extends NodeOperation> T perpareOperationInternal(Class<T> itf, Class<? extends T> cls2);

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends NodeOperation> void registerOperation(String name, Class<T> cls, Class<? extends T> cls2) {
		//
		this.operationInterfaceMap.put(name, cls);

		this.operationInterfaceImplementMap.put(cls, cls2);

	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public <W extends NodeWrapper> NodeSearchOperationI<W> prepareNodeSearch(Class<W> cls) {
		NodeSearchOperationI<W> rt = this.prepareOperation(NodeSearchOperationI.class);
		rt.nodeType(cls);
		return rt;
	}

	@Override
	public <W extends NodeWrapper> NodeCountOperationI<W> prepareNodeCount(Class<W> cls) {
		NodeCountOperationI<W> rt = this.prepareOperation(NodeCountOperationI.class);
		rt.nodeType(cls);
		return rt;
	}

	@Override
	public <W extends NodeWrapper> NodeSearchOperationI<W> prepareNodeSearch(NodeType ntype) {
		//
		NodeSearchOperationI<W> rt = this.prepareOperation(NodeSearchOperationI.class);
		rt.nodeType(ntype);
		return rt;
	}

	@Override
	public <T extends NodeWrapper> T getNewest(Class<T> wpcls, String field, Object value, boolean force) {
		return this.getNewest(wpcls, new String[] { field }, new Object[] { value }, force);
	}

	@Override
	public <T extends NodeWrapper> T getNewest(Class<T> wpcls, String[] fields, Object[] values, boolean force) {
		NodeSearchOperationI<T> qo = this.prepareNodeSearch(wpcls);
		for (int i = 0; i < fields.length; i++) {
			qo.propertyEq(fields[i], values[i]);
		}
		qo.sortTimestamp(true);
		NodeSearchResultI<T> rst = qo.execute().getResult();
		T node = rst.first(false);
		if (node == null) {
			if (force) {
				throw new RuntimeException("no node found for cls:" + wpcls + ",field:" + Arrays.asList(fields)
						+ ",value:" + Arrays.asList(values));
			}
			return null;
		}

		return node;

	}

	/*
	 * Oct 29, 2012
	 */
	@Override
	public <T extends NodeWrapper> T getByUid(Class<T> wpcls, String uid, boolean force) {
		NodeGetOperationI op = this.prepareOperation(NodeGetOperationI.class);
		T tt = ClassUtil.newInstance(wpcls);
		// TODO by class
		op.nodeType(tt.getNodeType());
		op.uniqueId(uid);
		op = op.execute();

		NodeResultI rst = op.getResult().assertNoError();
		T rt = rst.wrapNode(wpcls, force);

		return rt;
	}

	@Override
	public DataSchema getConfigurations() {
		return schema;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public <T extends NodeWrapper> boolean deleteByUid(Class<T> wpcls, String uid) {
		//
		NodeDeleteOperationI<T> nr = this.prepareOperation(NodeDeleteOperationI.class);
		nr.nodeType(wpcls);
		nr.uniqueId(uid);
		nr.execute().getResult().assertNoError();
		return true;
	}

	/*
	 * Dec 30, 2012TODO delete by query.
	 */
	@Override
	public <T extends NodeWrapper> int deleteById(Class<T> wpcls, String id) {
		int rt = 0;
		while (true) {
			T t = this.getNewestById(wpcls, id, false);

			if (t == null) {
				return rt;
			}
			rt++;
			String uid = t.getUniqueId();
			this.deleteByUid(wpcls, uid);
		}
	}

	/*
	 * May 3, 2013
	 */
	@Override
	public <T extends NodeWrapper> boolean updateByUid(Class<T> wpcls, String uid, String field, Object value) {
		//
		return this.updateByUid(wpcls, uid, new String[] { field }, new Object[] { value });
	}

	/*
	 * May 3, 2013
	 */
	@Override
	public <T extends NodeWrapper> boolean updateByUid(Class<T> wpcls, String uid, String[] fields,
			Object[] values) {
		NodeUpdateOperationI<T> op = this.prepareOperation(NodeUpdateOperationI.class);
		op.nodeType(wpcls);
		op.uniqueId(uid);
		for (int i = 0; i < fields.length; i++) {
			String key = fields[i];
			Object value = values[i];
			op.property(key, value);
		}
		BooleanResultI br = op.execute().getResult().assertNoError();

		return br.get(true);

	}
}
