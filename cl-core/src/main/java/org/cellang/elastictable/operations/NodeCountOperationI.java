/**
 *  
 */
package org.cellang.elastictable.operations;

import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.result.LongResultI;

/**
 * @author wu
 * 
 */
public interface NodeCountOperationI<W extends RowObject> extends
		NodeQueryOperationI<NodeCountOperationI<W>, W, LongResultI> {

}
