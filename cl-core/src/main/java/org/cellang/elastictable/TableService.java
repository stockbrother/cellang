/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package org.cellang.elastictable;

import java.util.List;

import org.cellang.elastictable.meta.DataSchema;
import org.cellang.elastictable.operations.NodeCountOperationI;
import org.cellang.elastictable.operations.NodeSearchOperationI;

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
public interface TableService {

	public <T extends TableOperation> T prepareOperation(String name);

	public <T extends TableOperation> T prepareOperation(Class<T> opc);

	public <W extends RowObject> NodeCountOperationI<W> prepareNodeCount(Class<W> cls);
	
	public <W extends RowObject> NodeSearchOperationI<W> prepareNodeSearch(Class<W> cls);

	public <W extends RowObject> NodeSearchOperationI<W> prepareNodeSearch(String type);

	public <T extends RowObject> List<T> getListNewestFirst(Class<T> wpcls, String[] fields,
			Object[] value, int from, int maxSize);

	public <T extends RowObject> List<T> getListNewestFirst(Class<T> wpcls, String fields, Object value,
			int from, int maxSize);

	// TODO remove
	public <T extends RowObject> T getByUid(Class<T> wpcls, String uid, boolean force);

	public <T extends RowObject> T getNewestById(Class<T> wpcls, String id, boolean force);

	public <T extends RowObject> List<T> getNewestListById(Class<T> wpcls, List<String> idL, boolean force,
			boolean reserveNull);

	public <T extends RowObject> T getNewest(Class<T> wpcls, String field, Object value, boolean force);

	public <T extends RowObject> T getNewest(Class<T> wpcls, String[] field, Object[] value, boolean force);

	public <T extends TableOperation> void registerOperation(String name, Class<T> itfCls,
			Class<? extends T> impCls);

	public void refresh();

	public <T extends RowObject> boolean deleteByUid(Class<T> wpcls, String uid);
	
	public <T extends RowObject> boolean updateByUid(Class<T> wpcls, String uid, String field, Object value);

	public <T extends RowObject> boolean updateByUid(Class<T> wpcls, String uid, String[] fields, Object[] values);

	public <T extends RowObject> int deleteById(Class<T> wpcls, String id);

	public DataSchema getConfigurations();

}
