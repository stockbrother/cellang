/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.elasticsearch;

import java.util.Map;

import org.cellang.elastictable.TableService;
import org.cellang.elastictable.meta.NodeMeta;
import org.cellang.elastictable.support.NodeSupport;
import org.elasticsearch.action.get.GetResponse;

/**
 * @author wu
 * 
 */
public class GetResponseNodeImpl extends NodeSupport {

	protected GetResponse response;

	/**
	 * @param type
	 * @param uid
	 */
	public GetResponseNodeImpl(String ntype, TableService ds, GetResponse gr) {
		super(ntype, gr.getId());
		this.response = gr;

		NodeMeta nc = ds.getConfigurations().getNodeConfig(ntype, true);
		Map<String, Object> old = this.response.getSourceAsMap();
		SearchHitNode.convertPropertiesFromStore(old, nc, this);

	}

}
