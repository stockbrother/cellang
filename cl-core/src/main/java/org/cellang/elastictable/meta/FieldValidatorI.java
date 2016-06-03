/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 28, 2012
 */
package org.cellang.elastictable.meta;

import org.cellang.core.lang.ErrorInfos;
import org.cellang.elastictable.RowObject;

/**
 * @author wu
 * 
 */
public interface FieldValidatorI {

	public void validate(FieldMeta fc, RowObject nw, ErrorInfos eis);

}
