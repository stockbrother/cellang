/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 20, 2013
 */
package org.cellang.elasticnode;

import org.cellang.elasticnode.meta.DataSchema;

/**
 * @author wu
 * 
 */
public interface NodeServiceFactory {

	public NodeService getDataService();

	public DataSchema getSchema();
	
	public void close();

}
