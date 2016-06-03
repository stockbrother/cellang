/**
 *  Feb 6, 2013
 */
package org.cellang.elastictable.elasticsearch;

import java.util.List;

import org.cellang.elastictable.TableOperation;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.meta.DataSchema;
import org.cellang.elastictable.operations.NodeCountOperationI;
import org.cellang.elastictable.operations.NodeSearchOperationI;

/**
 * @author wuzhen
 * 
 */
public class ProxyDataServiceImpl implements TableService {

	private TableService target;

	@Override
	public <T extends TableOperation> T prepareOperation(String name) {

		return target.prepareOperation(name);
	}

	@Override
	public <T extends TableOperation> T prepareOperation(Class<T> opc) {

		return target.prepareOperation(opc);
	}

	@Override
	public <W extends RowObject> NodeSearchOperationI<W> prepareNodeSearch(Class<W> cls) {

		return target.prepareNodeSearch(cls);
	}

	@Override
	public <W extends RowObject> NodeSearchOperationI<W> prepareNodeSearch(String ntype) {

		return target.prepareNodeSearch(ntype);
	}

	@Override
	public <T extends RowObject> List<T> getListNewestFirst(Class<T> wpcls, String[] fields,
			Object[] value, int from, int maxSize) {

		return target.getListNewestFirst(wpcls, fields, value, from, maxSize);
	}

	@Override
	public <T extends RowObject> List<T> getListNewestFirst(Class<T> wpcls, String fields, Object value,
			int from, int maxSize) {

		return target.getListNewestFirst(wpcls, fields, value, from, maxSize);
	}

	@Override
	public <T extends RowObject> T getByUid(Class<T> wpcls, String uid, boolean force) {

		return target.getByUid(wpcls, uid, force);
	}

	@Override
	public <T extends RowObject> T getNewestById(Class<T> wpcls, String id, boolean force) {

		return target.getNewestById(wpcls, id, force);
	}

	@Override
	public <T extends RowObject> List<T> getNewestListById(Class<T> wpcls, List<String> idL, boolean force,
			boolean reserveNull) {

		return target.getNewestListById(wpcls, idL, force, reserveNull);
	}

	@Override
	public <T extends RowObject> T getNewest(Class<T> wpcls, String field, Object value, boolean force) {

		return target.getNewest(wpcls, field, value, force);
	}

	@Override
	public <T extends RowObject> T getNewest(Class<T> wpcls, String[] field, Object[] value, boolean force) {

		return target.getNewest(wpcls, field, value, force);
	}

	@Override
	public <T extends TableOperation> void registerOperation(String name, Class<T> itfCls,
			Class<? extends T> impCls) {
		this.target.registerOperation(name, itfCls, impCls);
	}

	@Override
	public void refresh() {
		this.target.refresh();
	}

	@Override
	public <T extends RowObject> boolean deleteByUid(Class<T> wpcls, String uid) {

		return this.target.deleteByUid(wpcls, uid);

	}

	@Override
	public <T extends RowObject> int deleteById(Class<T> wpcls, String id) {

		return this.target.deleteById(wpcls, id);
	}

	@Override
	public DataSchema getConfigurations() {

		return target.getConfigurations();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cellang.elastictable.TableService#prepareNodeCount(java.lang.Class
	 * )
	 */
	@Override
	public <W extends RowObject> NodeCountOperationI<W> prepareNodeCount(Class<W> cls) {
		// TODO Auto-generated method stub
		return this.target.prepareNodeCount(cls);

	}

	/*
	 * May 3, 2013
	 */
	@Override
	public <T extends RowObject> boolean updateByUid(Class<T> wpcls, String uid, String field, Object value) {
		//
		return this.target.updateByUid(wpcls, uid, field, value);
	}

	/*
	 * May 3, 2013
	 */
	@Override
	public <T extends RowObject> boolean updateByUid(Class<T> wpcls, String uid, String[] fields,
			Object[] values) {
		return this.target.updateByUid(wpcls, uid, fields, values);
	}

}
