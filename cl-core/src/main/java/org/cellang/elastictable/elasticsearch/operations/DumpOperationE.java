/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.elasticsearch.operations;

import org.cellang.elastictable.ExecuteResult;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.elasticsearch.ElasticClient;
import org.cellang.elastictable.operations.DumpOperationI;
import org.cellang.elastictable.result.VoidResultI;
import org.cellang.elastictable.support.OperationSupport;
import org.cellang.elastictable.support.VoidResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

/**
 * @author wu
 * 
 */
public class DumpOperationE extends
		OperationSupport<DumpOperationI, VoidResultI> implements DumpOperationI {

	private ElasticClient elastic;

	/**
	 * @param ds
	 */
	public DumpOperationE(TableService ds) {
		super(new VoidResult(ds));
		this.elastic = (ElasticClient) ds;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(VoidResultI rst) throws Exception {
		int from = 0;
		int size = 10;
		while (true) {
			boolean done = dump(rst, from, size);
			if (done) {
				break;
			}
			from += size;
		}
	}

	protected boolean dump(ExecuteResult rst, int from, int size) {
		MatchAllQueryBuilder qb = new MatchAllQueryBuilder();
		Client client = elastic.getClient();
		SearchResponse response = client.prepareSearch()
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)//
				.setQuery(qb)//
				.setFrom(from)//
				.setSize(size)//
				.setExplain(true)//
				.execute()//
				.actionGet();
		SearchHits shs = response.getHits();

		long total = shs.getTotalHits();

		for (SearchHit sh : shs.getHits()) {
			// Map<String, SearchHitField> shMap = sh.getFields();
			StringBuilder sb = new StringBuilder().append("idx:")
					.append(sh.getIndex()).append("id:").append(sh.getId())
					.append(",type:").append(sh.getType())
					.append(sh.getSourceAsString());
			System.out.println(sb);

		}

		return from + shs.getHits().length >= total;

	}

}
