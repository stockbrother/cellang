/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.elasticsearch.operations;

import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.elasticsearch.support.ElasticOperationSupport;
import org.cellang.elasticnode.operations.RefreshOperationI;
import org.cellang.elasticnode.result.VoidResultI;
import org.cellang.elasticnode.support.VoidResult;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;

/**
 * @author wu
 * 
 */
public class RefreshOperationE extends
		ElasticOperationSupport<RefreshOperationI, VoidResultI> implements
		RefreshOperationI {

	/**
	 * @param ds
	 */
	public RefreshOperationE(NodeService ds) {
		super(new VoidResult(ds));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cellang.elasticnode.support.OperationSupport#executeInternal(
	 * org.cellang.elasticnode.ExecuteResult)
	 */
	@Override
	protected void executeInternal(VoidResultI rst) throws Exception {
		Client client = elastic.getClient();

		AdminClient aclient = client.admin();
		aclient.indices().prepareRefresh(this.elastic.getIndex()).execute()
				.actionGet();
		rst.set(Boolean.TRUE);//
	}

}
