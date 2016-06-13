/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.support;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.cellang.commons.util.ClassUtil;
import org.cellang.elastictable.TableOperation;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.meta.DataSchema;
import org.cellang.elastictable.operations.NodeCountOperationI;
import org.cellang.elastictable.operations.NodeDeleteOperationI;
import org.cellang.elastictable.operations.NodeGetOperationI;
import org.cellang.elastictable.operations.NodeSearchOperationI;
import org.cellang.elastictable.operations.NodeUpdateOperationI;
import org.cellang.elastictable.result.BooleanResultI;
import org.cellang.elastictable.result.NodeResultI;
import org.cellang.elastictable.result.NodeSearchResultI;

/**
 * @author wu
 * 
 */
public abstract class NodeServiceSupport implements TableService {

	protected DataSchema schema;

	protected Map<String, Class<? extends TableOperation>> operationInterfaceMap;

	protected Map<Class<? extends TableOperation>, Class<? extends TableOperation>> operationInterfaceImplementMap;

	public NodeServiceSupport(DataSchema ds) {
		this.schema = ds;
		this.operationInterfaceImplementMap = new HashMap<Class<? extends TableOperation>, Class<? extends TableOperation>>();
		this.operationInterfaceMap = new HashMap<String, Class<? extends TableOperation>>();
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends TableOperation> T prepareOperation(String name) {
		//
		Class<? extends TableOperation> cls = this.operationInterfaceMap.get(name);
		return (T) this.prepareOperation(cls);

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends TableOperation> T prepareOperation(Class<T> opc) {
		//
		Class<? extends T> cls2 = (Class<? extends T>) this.operationInterfaceImplementMap.get(opc);
		if (cls2 == null) {
			throw new RuntimeException("no impl class for:" + opc);
		}
		T rt = this.perpareOperationInternal(opc, cls2);
		rt.prepare();//
		return rt;
	}

	public abstract <T extends TableOperation> T perpareOperationInternal(Class<T> itf, Class<? extends T> cls2);

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends TableOperation> void registerOperation(String name, Class<T> cls, Class<? extends T> cls2) {
		//
		this.operationInterfaceMap.put(name, cls);

		this.operationInterfaceImplementMap.put(cls, cls2);

	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public <W extends RowObject> NodeSearchOperationI<W> prepareNodeSearch(Class<W> cls) {
		NodeSearchOperationI<W> rt = this.prepareOperation(NodeSearchOperationI.class);
		rt.nodeType(cls);
		return rt;
	}

	@Override
	public <W extends RowObject> NodeCountOperationI<W> prepareNodeCount(Class<W> cls) {
		NodeCountOperationI<W> rt = this.prepareOperation(NodeCountOperationI.class);
		rt.nodeType(cls);
		return rt;
	}

	@Override
	public <W extends RowObject> NodeSearchOperationI<W> prepareNodeSearch(String ntype) {
		//
		NodeSearchOperationI<W> rt = this.prepareOperation(NodeSearchOperationI.class);
		rt.nodeType(ntype);
		return rt;
	}

	@Override
	public <T extends RowObject> T getNewest(Class<T> wpcls, String field, Object value, boolean force) {
		return this.getNewest(wpcls, new String[] { field }, new Object[] { value }, force);
	}

	@Override
	public <T extends RowObject> T getNewest(Class<T> wpcls, String[] fields, Object[] values, boolean force) {
		NodeSearchOperationI<T> qo = this.prepareNodeSearch(wpcls);
		for (int i = 0; i < fields.length; i++) {
			qo.propertyEq(fields[i], values[i]);
		}
		qo.sortTimestamp(true);
		NodeSearchResultI<T> rst = qo.execute().getResult();
		rst.assertNoError();
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
	public <T extends RowObject> T getByUid(Class<T> wpcls, String uid, boolean force) {
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
	public <T extends RowObject> boolean deleteByUid(Class<T> wpcls, String uid) {
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
	public <T extends RowObject> int deleteById(Class<T> wpcls, String id) {
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
	public <T extends RowObject> boolean updateByUid(Class<T> wpcls, String uid, String field, Object value) {
		//
		return this.updateByUid(wpcls, uid, new String[] { field }, new Object[] { value });
	}

	/*
	 * May 3, 2013
	 */
	@Override
	public <T extends RowObject> boolean updateByUid(Class<T> wpcls, String uid, String[] fields,
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
