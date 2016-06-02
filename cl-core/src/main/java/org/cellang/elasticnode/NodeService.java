/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package org.cellang.elasticnode;

import java.util.List;

import org.cellang.elasticnode.meta.DataSchema;
import org.cellang.elasticnode.operations.NodeCountOperationI;
import org.cellang.elasticnode.operations.NodeSearchOperationI;

/**
 * @author wu
 *         <p>
 *         Data service is designed for small size of data access,it is not suit
 *         for data analysis.Because it as the client of data base,and the
 *         transfer protocol may be json over http.
 *         <p>
 *         Data service is designed for business facing,it means it's aim is not
 *         provide a general purpose data data interface,but a framework is
 *         provided for application layer extend.
 *         <p>
 *         For data analysis,it requires other means to be processed near more
 *         with the data base.
 * 
 */
public interface NodeService {

	public <T extends NodeOperation> T prepareOperation(String name);

	public <T extends NodeOperation> T prepareOperation(Class<T> opc);

	public <W extends NodeWrapper> NodeCountOperationI<W> prepareNodeCount(Class<W> cls);
	
	public <W extends NodeWrapper> NodeSearchOperationI<W> prepareNodeSearch(Class<W> cls);

	public <W extends NodeWrapper> NodeSearchOperationI<W> prepareNodeSearch(NodeType ntype);

	public <T extends NodeWrapper> List<T> getListNewestFirst(Class<T> wpcls, String[] fields,
			Object[] value, int from, int maxSize);

	public <T extends NodeWrapper> List<T> getListNewestFirst(Class<T> wpcls, String fields, Object value,
			int from, int maxSize);

	// TODO remove
	public <T extends NodeWrapper> T getByUid(Class<T> wpcls, String uid, boolean force);

	public <T extends NodeWrapper> T getNewestById(Class<T> wpcls, String id, boolean force);

	public <T extends NodeWrapper> List<T> getNewestListById(Class<T> wpcls, List<String> idL, boolean force,
			boolean reserveNull);

	public <T extends NodeWrapper> T getNewest(Class<T> wpcls, String field, Object value, boolean force);

	public <T extends NodeWrapper> T getNewest(Class<T> wpcls, String[] field, Object[] value, boolean force);

	public <T extends NodeOperation> void registerOperation(String name, Class<T> itfCls,
			Class<? extends T> impCls);

	public void refresh();

	public <T extends NodeWrapper> boolean deleteByUid(Class<T> wpcls, String uid);
	
	public <T extends NodeWrapper> boolean updateByUid(Class<T> wpcls, String uid, String field, Object value);

	public <T extends NodeWrapper> boolean updateByUid(Class<T> wpcls, String uid, String[] fields, Object[] values);

	public <T extends NodeWrapper> int deleteById(Class<T> wpcls, String id);

	public DataSchema getConfigurations();

}
