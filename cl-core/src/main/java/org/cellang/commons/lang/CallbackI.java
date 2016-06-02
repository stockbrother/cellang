/**
 * Jun 10, 2012
 */
package org.cellang.commons.lang;

/**
 * @author wu
 * 
 */
public interface CallbackI<IT, RT> {
	public RT execute(IT i);
}
