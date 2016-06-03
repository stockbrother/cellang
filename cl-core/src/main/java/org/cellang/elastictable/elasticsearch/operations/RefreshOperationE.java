/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.elasticsearch.operations;

import org.cellang.elastictable.TableService;
import org.cellang.elastictable.elasticsearch.support.ElasticOperationSupport;
import org.cellang.elastictable.operations.RefreshOperationI;
import org.cellang.elastictable.result.VoidResultI;
import org.cellang.elastictable.support.VoidResult;
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
	public RefreshOperationE(TableService ds) {
		super(new VoidResult(ds));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cellang.elastictable.support.OperationSupport#executeInternal(
	 * org.cellang.elastictable.ExecuteResult)
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
