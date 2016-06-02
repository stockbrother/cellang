/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package org.cellang.elasticnode.result;

import java.util.List;

import org.cellang.elasticnode.ExecuteResult;
import org.cellang.elasticnode.Node;
import org.cellang.elasticnode.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface NodeListResultI<R extends ExecuteResult<R, List<T>>, T extends Node>
		extends ListResultI<R, T> {

	public <X extends NodeWrapper> List<X> list(Class<X> class1);

}
