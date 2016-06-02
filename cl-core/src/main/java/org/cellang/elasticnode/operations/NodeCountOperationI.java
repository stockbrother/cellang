/**
 *  
 */
package org.cellang.elasticnode.operations;

import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.result.LongResultI;

/**
 * @author wu
 * 
 */
public interface NodeCountOperationI<W extends NodeWrapper> extends
		NodeQueryOperationI<NodeCountOperationI<W>, W, LongResultI> {

}
