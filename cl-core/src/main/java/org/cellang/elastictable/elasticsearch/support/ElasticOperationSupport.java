/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elastictable.elasticsearch.support;

import org.cellang.elastictable.ExecuteResult;
import org.cellang.elastictable.TableOperation;
import org.cellang.elastictable.elasticsearch.ElasticClient;
import org.cellang.elastictable.support.OperationSupport;

/**
 * @author wu
 * 
 */
public abstract class ElasticOperationSupport<O extends TableOperation<O, T>, T extends ExecuteResult<T, ?>>
		extends OperationSupport<O, T> {

	protected ElasticClient elastic;

	/**
	 * @param ds
	 */
	public ElasticOperationSupport(T rst) {
		super(rst);
		this.elastic = (ElasticClient) this.dataService;
	}

}
