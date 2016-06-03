/**
 *  Nov 28, 2012
 */
package org.cellang.elastictable.support;

import org.cellang.elastictable.TableService;
import org.cellang.elastictable.result.VoidResultI;

/**
 * @author wuzhen
 * 
 */
public class VoidResult extends ResultSupport<VoidResultI, Object> implements
		VoidResultI {

	/**
	 * @param ds
	 */
	public VoidResult(TableService ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

}
