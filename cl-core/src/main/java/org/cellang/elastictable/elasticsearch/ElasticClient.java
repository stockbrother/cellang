/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.elasticsearch;

import org.cellang.commons.lang.CallbackI;
import org.elasticsearch.client.Client;

/**
 * @author wu
 * 
 */
public interface ElasticClient {

	public String getIndex();

	public Client getClient();

	public <T> T executeInClient(CallbackI<Client, T> cb);

}
