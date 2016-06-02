/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elasticnode.elasticsearch.support;

import org.cellang.elasticnode.ExecuteResult;
import org.cellang.elasticnode.NodeOperation;
import org.cellang.elasticnode.elasticsearch.ElasticClient;
import org.cellang.elasticnode.support.OperationSupport;

/**
 * @author wu
 * 
 */
public abstract class ElasticOperationSupport<O extends NodeOperation<O, T>, T extends ExecuteResult<T, ?>>
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
