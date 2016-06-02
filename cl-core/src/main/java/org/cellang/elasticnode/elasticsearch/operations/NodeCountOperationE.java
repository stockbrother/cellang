/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.elasticsearch.operations;

import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.elasticsearch.ElasticClient;
import org.cellang.elasticnode.elasticsearch.support.NodeQueryOperationSupport;
import org.cellang.elasticnode.operations.NodeCountOperationI;
import org.cellang.elasticnode.result.LongResultI;
import org.cellang.elasticnode.support.LongResult;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class NodeCountOperationE<W extends NodeWrapper>
		extends NodeQueryOperationSupport<NodeCountOperationI<W>, W, LongResultI> implements NodeCountOperationI<W> {

	private static Logger LOG = LoggerFactory.getLogger(NodeCountOperationE.class);

	private ElasticClient elastic;

	/**
	 * @param ds
	 */
	public NodeCountOperationE(NodeService ds) {
		super(ds, new LongResult(ds));
		this.elastic = (ElasticClient) ds;

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(LongResultI rst) throws Exception {
		Client client = elastic.getClient();
		BoolQueryBuilder qb = this.buildQuery(rst);
		if (rst.hasError()) {
			return;
		}
		String idx = this.elastic.getIndex();

		// CountRequestBuilder crb = client.prepareCount(idx)
		// .setQuery(qb)//
		// ;
		// CountResponse response = crb.execute()//
		// .actionGet();
		//
		//long ct = response.getCount();
		//rst.set(0L);
		throw new RuntimeException("TODO");
	}

}
