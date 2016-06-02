/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elasticnode.result;

import java.util.List;

import org.cellang.elasticnode.ExecuteResult;

/**
 * @author wu
 * 
 */
public interface ListResultI<R extends ExecuteResult<R, List<T>>, T> extends
		ExecuteResult<R, List<T>> {
	public List<T> list();

	public T single(boolean force);

	public T first(boolean force);

	public int size();

}
